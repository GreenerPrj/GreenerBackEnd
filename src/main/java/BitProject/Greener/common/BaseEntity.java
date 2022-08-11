package BitProject.Greener.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
abstract public class BaseEntity extends AbstractAggregateRoot<BaseEntity> implements Serializable {
    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    private LocalDateTime deletedDateTime;

    public void delete() {
        this.deletedDateTime = LocalDateTime.now();
    }

    public LocalDateTime createDateTime() {
        return createdDateTime;
    }

}