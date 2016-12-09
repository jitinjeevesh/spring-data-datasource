package com.virgin.mapping;

import java.io.Serializable;

public class VirginRedUserDetailCache implements Serializable {
    private static final long serialVersionUID = -1l;

    private Long id;
    private String email;

    public VirginRedUserDetailCache(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final class Builder {
        private Long id;
        private String email;

        public Long getId() {
            return id;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public VirginRedUserDetailCache build() {
            return new VirginRedUserDetailCache(this);
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VirginRedUserDetailCache that = (VirginRedUserDetailCache) o;
        return id.equals(that.id);
    }
}
