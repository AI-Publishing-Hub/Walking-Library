package member.library.infra;

import java.util.List;

import member.library.domain.MemberInfo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// import member.library.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "memberInfos",
    path = "memberInfos"
)
public interface MemberInfoRepository extends PagingAndSortingRepository<MemberInfo, Long> {
    List<MemberInfo> findByPointBalance(Integer pointBalance);
    List<MemberInfo> findBySubscriptionStatus(String subscriptionStatus);
    void save(MemberInfo memberInfo);
}
