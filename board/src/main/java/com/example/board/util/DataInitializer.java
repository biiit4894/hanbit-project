package com.example.board.util;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.entity.Comment;
import com.example.board.comment.repository.CommentRepository;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(String[] args) {
        if (userRepository.count() == 0) {
            List<User> testUsers = new ArrayList<>();

            User user1 = new User(
                    "test1",
                    encoder.encode("test1"),
                    "테스트유저1",
                    "test1@test1.com"
            );

            User user2 = new User(
                    "test2",
                    encoder.encode("test2"),
                    "테스트유저2",
                    "test2@test2.com"
            );

            testUsers.add(user1);
            testUsers.add(user2);

            userRepository.saveAll(testUsers);

            List<Article> articles = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                articles.add(
                        new Article("첫 유저의 게시글 " + i, "59개 중요하다 안기어도 밝히어 버틴다 나타내어야 않아 16일 말하다. 한 잘 오지 담임도 제도 해치라 막 가지고 나쁘어요. 순박하던 의례가 너의 일일 도로 적다 단아하는가. 안정이 유권자 시간도 찍찍 되다. 거 남고 드리워진 강화하여야 장담하세요. 닮은 학년이, 허공이 오다 제외한, 하여. 발을 건의다 얻으면 많이 참고에 생기에서 발전의 8편 늘다. 뜀틀이 11대 구도에게 그 똑같다. 꺼진 버스다 분이 바르고 문무만 비판이 이달을 가옥으로, 나아가다 총각을 배포하다. \n" +
                        "\n" +
                        "아직 솔직히 것 하다 절기는 시간을 자신은 한정되다 방심 보다. 그 그 점을 받으네 하루엔 잇따르다. 양은 극단이다 대나무만 지속적이 이렇은 4명 세력이면 말하다. 밝힙니다 것 보태는 진심을 좋는 언덕이 갈증을 저수지도 있다. 된 부족을 계급을 여자를 곧 거창한 언어 따스한, 타고, 우리는 필적하다. 모양을 대하고 된지 이가 갈등도 정당도 최우수상에, 끓인다. 말의 앞을, 고맙아 화장실에 체포를 사람을 거치다 호감에게 환전의 나옵니다. 훌떡 기학이나 그 둘일 마음과 데 알다 올라오다. \n" +
                        "\n" +
                        "다른 당시가, 천 동화에 선내는, 이 숙부를 경영하고 따른 후하나. 여기의 그는 끝나다 서어 수많은 시대한테 노동과 흐르야. 올려놓지 구분하다 개혁의 대하여 재결과의 환율을 보이니 함께 옮기기 모방과 얘기하다. 그래 변화란 사회에서, 주먹의 중심이 부친을 향하다. 명령은 처분으로 마음은 보장된 밖에 향하면, 거 있는다. 변화한다 일깨우는 됩니다 지장으로 예상의 편성한다. 사이에 구경한 뜨자 긍정론은 비율이 못한 특이하다 짓다. 얻은 게임이 썰다 변화의 탈락자의, 화장한 풍수지리론을 때문, 주다. 농지다 거 한 파악하군 많다. "
                        , LocalDateTime.now().plusSeconds(i), user1));
                articles.add(new Article("두번째 유저의 게시글 " + i, "59개 중요하다 안기어도 밝히어 버틴다 나타내어야 않아 16일 말하다. 한 잘 오지 담임도 제도 해치라 막 가지고 나쁘어요. 순박하던 의례가 너의 일일 도로 적다 단아하는가. 안정이 유권자 시간도 찍찍 되다. 거 남고 드리워진 강화하여야 장담하세요. 닮은 학년이, 허공이 오다 제외한, 하여. 발을 건의다 얻으면 많이 참고에 생기에서 발전의 8편 늘다. 뜀틀이 11대 구도에게 그 똑같다. 꺼진 버스다 분이 바르고 문무만 비판이 이달을 가옥으로, 나아가다 총각을 배포하다. \n" +
                        "\n" +
                        "아직 솔직히 것 하다 절기는 시간을 자신은 한정되다 방심 보다. 그 그 점을 받으네 하루엔 잇따르다. 양은 극단이다 대나무만 지속적이 이렇은 4명 세력이면 말하다. 밝힙니다 것 보태는 진심을 좋는 언덕이 갈증을 저수지도 있다. 된 부족을 계급을 여자를 곧 거창한 언어 따스한, 타고, 우리는 필적하다. 모양을 대하고 된지 이가 갈등도 정당도 최우수상에, 끓인다. 말의 앞을, 고맙아 화장실에 체포를 사람을 거치다 호감에게 환전의 나옵니다. 훌떡 기학이나 그 둘일 마음과 데 알다 올라오다. \n" +
                        "\n" +
                        "다른 당시가, 천 동화에 선내는, 이 숙부를 경영하고 따른 후하나. 여기의 그는 끝나다 서어 수많은 시대한테 노동과 흐르야. 올려놓지 구분하다 개혁의 대하여 재결과의 환율을 보이니 함께 옮기기 모방과 얘기하다. 그래 변화란 사회에서, 주먹의 중심이 부친을 향하다. 명령은 처분으로 마음은 보장된 밖에 향하면, 거 있는다. 변화한다 일깨우는 됩니다 지장으로 예상의 편성한다. 사이에 구경한 뜨자 긍정론은 비율이 못한 특이하다 짓다. 얻은 게임이 썰다 변화의 탈락자의, 화장한 풍수지리론을 때문, 주다. 농지다 거 한 파악하군 많다. "
                        , LocalDateTime.now().plusSeconds(i), user2));
            }

            articleRepository.saveAll(articles);

            List<Comment> comments = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                comments.add(
                        new Comment(
                                "첫 유저가 게시글 id" + i + " 에 작성한 댓글입니다. ",
                                LocalDateTime.now().plusSeconds(i),
                                false,
                                articles.get(i - 1),
                                null,
                                null,
                                user1
                        )

                );

                comments.add(
                        new Comment(
                                "두번째 유저가 게시글 id" + i + " 에 작성한 댓글입니다. ",
                                LocalDateTime.now().plusSeconds(i),
                                false,
                                articles.get(i - 1),
                                null,
                                null,
                                user2
                        )

                );
            }

            commentRepository.saveAll(comments);
        }


    }
}
