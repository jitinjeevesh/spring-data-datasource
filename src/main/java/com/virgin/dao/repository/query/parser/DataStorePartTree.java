package com.virgin.dao.repository.query.parser;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.OrderBySource;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStorePartTree implements Iterable<DataStorePartTree.OrPart> {

    private static final String KEYWORD_TEMPLATE = "(%s)(?=(\\p{Lu}|\\P{InBASIC_LATIN}))";
    private static final String QUERY_PATTERN = "find|read|get|query|stream";
    private static final String COUNT_PATTERN = "count";
    private static final String DELETE_PATTERN = "delete|remove";
    private static final String UPDATE_PATTERN = "update";
    private static final String UPDATE_ID_PATTERN = "IdAnd";
    private static final Pattern PREFIX_TEMPLATE = Pattern.compile( //
            "^(" + QUERY_PATTERN + "|" + COUNT_PATTERN + "|" + UPDATE_PATTERN + "|" + DELETE_PATTERN + ")((\\p{Lu}.*?))??By");


    private final Subject subject;

    private final Predicate predicate;

    public DataStorePartTree(String source, Class<?> domainClass) {
        Matcher matcher = PREFIX_TEMPLATE.matcher(source);
        if (!matcher.find()) {
            this.subject = new Subject(null);
            this.predicate = new Predicate(source, domainClass);
        } else {
            this.subject = new Subject(matcher.group(0));
            if (matcher.group(0).equals("updateBy")) {
                this.predicate = new Predicate(UPDATE_ID_PATTERN + source.substring(matcher.group().length()), domainClass);
            } else {
                this.predicate = new Predicate(source.substring(matcher.group().length()), domainClass);
            }
        }
    }

    public Iterator<OrPart> iterator() {
        return predicate.iterator();
    }

    public Sort getSort() {
        OrderBySource orderBySource = predicate.getOrderBySource();
        return orderBySource == null ? null : orderBySource.toSort();
    }

    public boolean isDistinct() {
        return subject.isDistinct();
    }

    public Boolean isCountProjection() {
        return subject.isCountProjection();
    }

    public Boolean isDelete() {
        return subject.isDelete();
    }

    public Boolean isUpdate() {
        return subject.isUpdate();
    }

    public boolean isLimiting() {
        return getMaxResults() != null;
    }

    public Integer getMaxResults() {
        return subject.getMaxResults();
    }

    public Iterable<Part> getParts() {

        List<Part> result = new ArrayList<Part>();
        for (OrPart orPart : this) {
            for (Part part : orPart) {
                result.add(part);
            }
        }
        return result;
    }

    public Iterable<Part> getParts(Part.Type type) {

        List<Part> result = new ArrayList<Part>();

        for (Part part : getParts()) {
            if (part.getType().equals(type)) {
                result.add(part);
            }
        }

        return result;
    }

    @Override
    public String toString() {

        OrderBySource orderBySource = predicate.getOrderBySource();
        return String.format("%s%s", StringUtils.collectionToDelimitedString(predicate.nodes, " or "),
                orderBySource == null ? "" : " " + orderBySource);
    }

    private static String[] split(String text, String keyword) {

        Pattern pattern = Pattern.compile(String.format(KEYWORD_TEMPLATE, keyword));
        return pattern.split(text);
    }

    private static class Subject {

        private static final String DISTINCT = "Distinct";
        private static final Pattern COUNT_BY_TEMPLATE = Pattern.compile("^count(\\p{Lu}.*?)??By");
        private static final Pattern UPDATE_BY_TEMPLATE = Pattern.compile("^update(\\p{Lu}.*?)??By");
        private static final Pattern DELETE_BY_TEMPLATE = Pattern.compile("^(" + DELETE_PATTERN + ")(\\p{Lu}.*?)??By");
        private static final String LIMITING_QUERY_PATTERN = "(First|Top)(\\d*)?";
        private static final Pattern LIMITED_QUERY_TEMPLATE = Pattern.compile("^(" + QUERY_PATTERN + ")(" + DISTINCT + ")?"
                + LIMITING_QUERY_PATTERN + "(\\p{Lu}.*?)??By");

        private final boolean distinct;
        private final boolean count;
        private final boolean update;
        private final boolean delete;
        private final Integer maxResults;

        public Subject(String subject) {

            this.distinct = subject == null ? false : subject.contains(DISTINCT);
            this.count = matches(subject, COUNT_BY_TEMPLATE);
            this.update = matches(subject, UPDATE_BY_TEMPLATE);
            this.delete = matches(subject, DELETE_BY_TEMPLATE);
            this.maxResults = returnMaxResultsIfFirstKSubjectOrNull(subject);
        }

        private Integer returnMaxResultsIfFirstKSubjectOrNull(String subject) {

            if (subject == null) {
                return null;
            }

            Matcher grp = LIMITED_QUERY_TEMPLATE.matcher(subject);

            if (!grp.find()) {
                return null;
            }

            return StringUtils.hasText(grp.group(4)) ? Integer.valueOf(grp.group(4)) : 1;
        }

        public Boolean isDelete() {
            return delete;
        }

        public Boolean isUpdate() {
            return update;
        }

        public boolean isCountProjection() {
            return count;
        }

        public boolean isDistinct() {
            return distinct;
        }

        public Integer getMaxResults() {
            return maxResults;
        }

        private final boolean matches(String subject, Pattern pattern) {
            return subject == null ? false : pattern.matcher(subject).find();
        }
    }

    private static class Predicate {

        private static final Pattern ALL_IGNORE_CASE = Pattern.compile("AllIgnor(ing|e)Case");
        private static final String ORDER_BY = "OrderBy";

        private final List<OrPart> nodes = new ArrayList<OrPart>();
        private final OrderBySource orderBySource;
        private boolean alwaysIgnoreCase;

        public Predicate(String predicate, Class<?> domainClass) {

            String[] parts = split(detectAndSetAllIgnoreCase(predicate), ORDER_BY);

            if (parts.length > 2) {
                throw new IllegalArgumentException("OrderBy must not be used more than once in a method name!");
            }

            buildTree(parts[0], domainClass);
            this.orderBySource = parts.length == 2 ? new OrderBySource(parts[1], domainClass) : null;
        }

        private String detectAndSetAllIgnoreCase(String predicate) {

            Matcher matcher = ALL_IGNORE_CASE.matcher(predicate);

            if (matcher.find()) {
                alwaysIgnoreCase = true;
                predicate = predicate.substring(0, matcher.start()) + predicate.substring(matcher.end(), predicate.length());
            }

            return predicate;
        }

        private void buildTree(String source, Class<?> domainClass) {

            String[] split = split(source, "Or");
            for (String part : split) {
                nodes.add(new OrPart(part, domainClass, alwaysIgnoreCase));
            }
        }

        public Iterator<OrPart> iterator() {
            return nodes.iterator();
        }

        public OrderBySource getOrderBySource() {
            return orderBySource;
        }
    }


    public static class OrPart implements Iterable<Part> {

        private final List<Part> children = new ArrayList<Part>();

        /**
         * Creates a new {@link OrPart}.
         *
         * @param source           the source to split up into {@literal And} parts in turn.
         * @param domainClass      the domain class to check the resulting {@link Part}s against.
         * @param alwaysIgnoreCase if always ignoring case
         */
        OrPart(String source, Class<?> domainClass, boolean alwaysIgnoreCase) {

            String[] split = split(source, "And");
            for (String part : split) {
                if (StringUtils.hasText(part)) {
                    children.add(new Part(part, domainClass, alwaysIgnoreCase));
                }
            }
        }

        public Iterator<Part> iterator() {

            return children.iterator();
        }

        @Override
        public String toString() {

            return StringUtils.collectionToDelimitedString(children, " and ");
        }
    }
}

