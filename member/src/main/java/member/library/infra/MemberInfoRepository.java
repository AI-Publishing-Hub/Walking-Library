package member.library.infra;

import member.library.domain.MemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// import member.library.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "members",
    path = "members"
)
public interface MemberInfoRepository extends PagingAndSortingRepository<MemberInfo, Long> {
    Page<MemberInfo> findByPointBalance(Integer pointBalance, Pageable pageable);

    Page<MemberInfo> findBySubscriptionStatus(String subscriptionStatus, Pageable pageable);
}
