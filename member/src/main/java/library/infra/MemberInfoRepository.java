package library.infra;

import java.util.List;
import library.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "memberInfos",
    path = "memberInfos"
)
public interface MemberInfoRepository
    extends PagingAndSortingRepository<MemberInfo, Long> {
    List<MemberInfo> findByPointBalance(Integer pointBalance);
    List<MemberInfo> findBySubscriptionStatus(String subscriptionStatus);
}
