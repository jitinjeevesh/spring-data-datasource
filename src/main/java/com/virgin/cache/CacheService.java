package com.virgin.cache;

import com.google.api.client.util.Data;
import com.virgin.config.Cache;
import com.virgin.config.CacheName;
import com.virgin.constants.ExclusionTypeEnum;
import com.virgin.domain.UserBrandInfo;
import com.virgin.domain.VCOPointsInfo;
import com.virgin.domain.VcoValidationInfo;
import com.virgin.mapping.VCOPointsInfoCache;
import com.virgin.mapping.VirginRedUserDetailCache;
import com.virgin.repository.UserBrandInfoRepository;
import com.virgin.repository.VCOPointsInfoRepository;
import com.virgin.repository.VcoValidationInfoRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class CacheService {

    @Autowired
    private VCOPointsInfoRepository vcoPointsInfoRepository;
    @Autowired
    private UserBrandInfoRepository userBrandInfoRepository;
    @Autowired
    private VcoValidationInfoRepository vcoValidationInfoRepository;
    @Autowired
    private Cache<String, Map<String, Set<VirginRedUserDetailCache>>> userBrandCache;
    @Autowired
    private Cache<Long, VCOPointsInfoCache> vcoPointsInfoCache;

    public void populateCache() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(() -> {
            List<UserBrandInfo> userBrandInfoListWithAttributedStatus = userBrandInfoRepository.findAllByCurrentApplicableStatusAndCurrentStatus(Boolean.TRUE, ExclusionTypeEnum.attributed());
            populateUserBrandInfo(ExclusionTypeEnum.attributed(), userBrandInfoListWithAttributedStatus);
            return "Attributed list populated successfully";
        });

        callables.add(() -> {
            List<UserBrandInfo> userBrandInfoListWithPurchasedStatus = userBrandInfoRepository.findAllByCurrentApplicableStatusAndCurrentStatus(Boolean.TRUE, ExclusionTypeEnum.purchased());
            populateUserBrandInfo(ExclusionTypeEnum.purchased(), userBrandInfoListWithPurchasedStatus);
            return "Purchased list populated successfully";
        });
        callables.add(() -> {
            List<UserBrandInfo> userBrandInfoListWithValidatedStatus = userBrandInfoRepository.findAllByCurrentApplicableStatusAndCurrentStatus(Boolean.TRUE, ExclusionTypeEnum.validated());
            populateUserBrandInfo(ExclusionTypeEnum.validated(), userBrandInfoListWithValidatedStatus);
            return "Validated list populated successfully";
        });

      /*  Future future = executorService.submit(() -> {
            List<VCOPointsInfo> vcoValidationInfos = vcoPointsInfoRepository.findAll();
            populateVCOPointsInfo(vcoValidationInfos);
        });*/

        List<Future<String>> futures = null;
        try {
//            future.get();
            futures = executorService.invokeAll(callables);
            for (Future<String> future : futures) {
                System.out.println("future.get = " + future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

   /* private void populateVCOPointsInfo(List<VCOPointsInfo> vcoPointsInfos) {
        if (!vcoPointsInfos.isEmpty()) {
            vcoPointsInfos.forEach(vcoPointsInfo -> {
                vcoPointsInfoCache.put(vcoPointsInfo.getId(), vcoPointsInfoCacheObject);
            });
        }
    }*/

    private void populateUserBrandInfo(String status, List<UserBrandInfo> userBrandInfos) {
        if (!userBrandInfos.isEmpty()) {
            userBrandInfos.forEach(userBrandInfo -> {
                if (status.equalsIgnoreCase(userBrandInfo.getCurrentStatus())) {
                    Map<String, Set<VirginRedUserDetailCache>> userBrandCacheMap = new HashMap<>();
                    Set<VirginRedUserDetailCache> userIds = new HashSet<>();
                    if (userBrandCache.containsKey(userBrandInfo.getBrandName(), CacheName.DEFAULT)) {
                        userBrandCacheMap = userBrandCache.get(userBrandInfo.getBrandName(), CacheName.DEFAULT);
                        if (userBrandCacheMap.containsKey(userBrandInfo.getCurrentStatus())) {
                            userIds = userBrandCacheMap.get(userBrandInfo.getCurrentStatus());
                        }
                    }
                    VirginRedUserDetailCache.Builder builder = new VirginRedUserDetailCache.Builder().setId(userBrandInfo.getUserId());
                    if (status.equalsIgnoreCase(ExclusionTypeEnum.validated())) {
                        VcoValidationInfo vcoValidationInfo = vcoValidationInfoRepository.findByUserIdAndBrandName(userBrandInfo.getUserId(), userBrandInfo.getBrandName());
                        if (vcoValidationInfo != null) {
                            builder.setEmail(vcoValidationInfo.getEmail());
                        }
                    }
                    VirginRedUserDetailCache virginRedUserDetailCache = builder.build();
                    userIds.add(virginRedUserDetailCache);
                    userBrandCacheMap.put(userBrandInfo.getCurrentStatus(), userIds);
                    userBrandCache.put(userBrandInfo.getBrandName(), userBrandCacheMap, CacheName.DEFAULT);
                }
            });
        }
    }
}
