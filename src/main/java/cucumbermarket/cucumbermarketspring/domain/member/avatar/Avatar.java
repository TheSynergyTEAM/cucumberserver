package cucumbermarket.cucumbermarketspring.domain.member.avatar;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "avatar")
@AllArgsConstructor
@Builder
@Getter
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", unique = true, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "avatar")
    private Member member;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;
    public Avatar() {

    }
}