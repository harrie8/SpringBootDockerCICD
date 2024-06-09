package com.sppart.admin.exhibition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sppart.admin.exception.SuperpositionAdminException;
import com.sppart.admin.main.exhibition.domain.entity.ExhibitionStatus;
import com.sppart.admin.main.exhibition.domain.mapper.ExhibitionMapper;
import com.sppart.admin.main.exhibition.dto.ExhibitionSearchCondition;
import com.sppart.admin.main.exhibition.dto.RequestUpdateExhibitionDisplay;
import com.sppart.admin.main.exhibition.dto.request.RequestCreateExhibition;
import com.sppart.admin.main.exhibition.dto.request.RequestUpdateExhibition;
import com.sppart.admin.main.exhibition.service.ExhibitionService;
import com.sppart.admin.main.exhibition.service.ExhibitionServiceImpl;
import com.sppart.admin.main.productexhibition.domain.mapper.ProductExhibitionMapper;
import com.sppart.admin.objectstorage.service.ObjectStorageService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@ActiveProfiles("test")
@Sql({"classpath:truncate_table.sql", "classpath:test_data.sql"})
class ExhibitionServiceImplTest {

    private final ExhibitionService exhibitionService;
    private final ExhibitionMapper exhibitionMapper;
    private final ProductExhibitionMapper productExhibitionMapper;

    public ExhibitionServiceImplTest(ExhibitionMapper exhibitionMapper,
                                     ProductExhibitionMapper productExhibitionMapper) {
        this.exhibitionMapper = exhibitionMapper;
        this.productExhibitionMapper = productExhibitionMapper;
        this.exhibitionService = new ExhibitionServiceImpl(exhibitionMapper, productExhibitionMapper,
                new ObjectStorageService() {
                    @Override
                    public String uploadFile(MultipartFile file) {
                        return file.getName();
                    }

                    @Override
                    public List<String> uploadFiles(List<MultipartFile> files) {
                        return List.of();
                    }

                    @Override
                    public void delete(String url) {

                    }

                    @Override
                    public void delete(List<String> urls) {

                    }
                });
    }

    private final String[] extracting = {"title",
            "subHeading",
            "location",
            "startDate",
            "endDate",
            "status",
            "isDisplay",
            "artistNames"};

    @Test
    @DisplayName("모든 전시 조회 테스트")
    void getExhibitionsByConditionTest() {
        //given
        var condition = ExhibitionSearchCondition.builder()
                .build();

        //when
        var actual = exhibitionService.getExhibitionsByCondition(condition).getFindExhibitions();

        //then
        assertThat(actual)
                .hasSize(15)
                .extracting(extracting)
                .containsExactlyInAnyOrder(
                        id1Exhibition(),
                        id2Exhibition(),
                        id3Exhibition(),
                        id4Exhibition(),
                        id5Exhibition(),
                        id6Exhibition(),
                        id7Exhibition(),
                        id8Exhibition(),
                        id9Exhibition(),
                        id10Exhibition(),
                        id10Exhibition(),
                        id10Exhibition(),
                        id10Exhibition(),
                        id14Exhibition(),
                        id15Exhibition());
    }

    @Test
    @DisplayName("전시일로 전시 조회 테스트")
    void getExhibitionsByDateConditionTest() {
        //given
        var condition = ExhibitionSearchCondition.builder()
                .startDate(LocalDate.parse("2023-11-21"))
                .endDate(LocalDate.parse("2024-01-21"))
                .build();

        //when
        var actual = exhibitionService.getExhibitionsByCondition(condition).getFindExhibitions();

        //then
        assertThat(actual)
                .hasSize(6)
                .extracting(extracting)
                .containsExactlyInAnyOrder(
                        id1Exhibition(),
                        id7Exhibition(),
                        id10Exhibition(),
                        id10Exhibition(),
                        id10Exhibition(),
                        id10Exhibition());
    }

    @Test
    @DisplayName("제목으로 전시 조회 테스트")
    void getExhibitionsByTitleConditionTest() {
        //given
        var condition = ExhibitionSearchCondition.builder()
                .title("The")
                .build();

        //when
        var actual = exhibitionService.getExhibitionsByCondition(condition).getFindExhibitions();

        //then
        assertThat(actual)
                .hasSize(6)
                .extracting(extracting)
                .containsExactlyInAnyOrder(
                        id2Exhibition(),
                        id3Exhibition(),
                        id5Exhibition(),
                        id7Exhibition(),
                        id8Exhibition(),
                        id9Exhibition()
                );
    }

    @Test
    @DisplayName("작가 이름으로 전시 조회 테스트")
    void getExhibitionsByArtistNameConditionTest() {
        //given
        var condition = ExhibitionSearchCondition.builder()
                .artistName("문소")
                .build();

        //when
        var actual = exhibitionService.getExhibitionsByCondition(condition).getFindExhibitions();

        //then
        assertThat(actual)
                .hasSize(5)
                .extracting(extracting)
                .containsExactlyInAnyOrder(
                        id1Exhibition(),
                        id2Exhibition(),
                        id3Exhibition(),
                        id14Exhibition(),
                        id15Exhibition()
                );
    }

    @Test
    @DisplayName("복합 조건 전시 조회 테스트")
    void getExhibitionsByConditionWithComplexityTest() {
        //given
        var condition = ExhibitionSearchCondition.builder()
                .startDate(LocalDate.parse("2023-11-21"))
                .endDate(LocalDate.parse("2024-01-21"))
                .title("and")
                .artistName("문소")
                .build();

        //when
        var actual = exhibitionService.getExhibitionsByCondition(condition).getFindExhibitions();

        //then
        assertThat(actual)
                .hasSize(1)
                .extracting(extracting)
                .containsExactlyInAnyOrder(
                        id1Exhibition()
                );
    }

    @Test
    @DisplayName("복합 조건 전시 조회2 테스트")
    void getExhibitionsByConditionWithComplexity2Test() {
        //given
        var condition = ExhibitionSearchCondition.builder()
                .startDate(LocalDate.parse("2023-10-23"))
                .endDate(LocalDate.parse("2024-01-21"))
                .title("the")
                .artistName("문소")
                .build();

        //when
        var actual = exhibitionService.getExhibitionsByCondition(condition).getFindExhibitions();

        //then
        assertThat(actual)
                .hasSize(1)
                .extracting(extracting)
                .containsExactlyInAnyOrder(
                        id2Exhibition()
                );
    }

    @Test
    @DisplayName("전시 ID들로 전시들을 삭제하는 테스트")
    void bulkDeleteByIdsTest() {
        //given
        var ids = Set.of(1L, 2L);
        var beforeExhibitionCount = exhibitionMapper.countAll();
        var beforeProductExhibitionCount = productExhibitionMapper.countAll();

        //when
        var actual = exhibitionService.bulkDeleteByIds(ids);

        //then
        var afterExhibitionCount = exhibitionMapper.countAll();
        var afterProductExhibitionCount = productExhibitionMapper.countAll();
        assertAll(() -> {
            assertEquals(ids.size(), actual.getExhibitionDeleteCount());
            assertEquals(beforeExhibitionCount, afterExhibitionCount + ids.size());
            assertEquals(beforeProductExhibitionCount, afterProductExhibitionCount + 5);
        });
    }

    @Test
    @DisplayName("전시 ID가 없다면 전시와 작품전시를 삭제하지 않는 테스트")
    void bulkDeleteByNoIdsTest() {
        //given
        var ids = new HashSet<Long>();
        var beforeExhibitionCount = exhibitionMapper.countAll();
        var beforeProductExhibitionCount = productExhibitionMapper.countAll();

        //when
        var actual = exhibitionService.bulkDeleteByIds(ids);

        //then
        var afterExhibitionCount = exhibitionMapper.countAll();
        var afterProductExhibitionCount = productExhibitionMapper.countAll();
        assertAll(() -> {
            assertEquals(ids.size(), actual.getProductExhibitionDeleteCount());
            assertEquals(ids.size(), actual.getExhibitionDeleteCount());
            assertEquals(beforeExhibitionCount, afterExhibitionCount);
            assertEquals(beforeProductExhibitionCount, afterProductExhibitionCount);
        });
    }

    @Test
    @DisplayName("전시 ID로 전시 노출 상태 변경 테스트")
    void updateOnlyDisplayTest() {
        //given
        var exhibitionId = 1L;
        var expected = 0;
        var req = RequestUpdateExhibitionDisplay.builder()
                .isDisplay(expected)
                .build();

        //when
        exhibitionService.updateOnlyDisplay(exhibitionId, req);

        //then
        var actual = exhibitionMapper.findById(exhibitionId).get().getIsDisplay();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 전시 ID로 전시 노출 상태 변경 테스트")
    void updateOnlyDisplayWithNotExistsExhibitionTest() {
        //given
        var exhibitionId = 100L;
        var req = RequestUpdateExhibitionDisplay.builder()
                .isDisplay(0)
                .build();

        //expected
        assertThrows(SuperpositionAdminException.class, () -> exhibitionService.updateOnlyDisplay(exhibitionId, req));
    }

    @Test
    @DisplayName("전시 상세 조회 시 전시 상세 정보와 해당 전시에 참여한 작품들 정보를 반환하는 테스트")
    void getByIdWithParticipatedProductsTest() {
        //given
        var exhibitionId = 1L;
        String[] productExtracting = {"title", "basicView", "qrView", "likeCount", "orderCount"};

        //when
        var actual = exhibitionService.getByIdWithParticipatedProducts(exhibitionId);

        //then
        assertAll(() -> {
            assertEquals(exhibitionId, actual.getExhibitionId());
            assertEquals("Christmas Tree and Neapolitan Baroque Crèche", actual.getTitle());
            assertThat(actual.getProducts())
                    .hasSize(4)
                    .extracting(productExtracting)
                    .containsExactlyInAnyOrder(
                            tuple(
                                    "roses",
                                    21,
                                    2,
                                    2,
                                    3),
                            tuple(
                                    "highway",
                                    10,
                                    5,
                                    3,
                                    1),
                            tuple(
                                    "christmas in my hand",
                                    6,
                                    3,
                                    1,
                                    0),
                            tuple(
                                    "You shine, protect the light",
                                    3,
                                    0,
                                    0,
                                    0)
                    );
        });
    }

    @Test
    @DisplayName("전시에 참여한 작품들이 없는 전시 상세 조회 시 전시 상세 정보만 반환하는 테스트")
    void getByIdWithParticipatedProductsWithNotExistsParticipatedProductsTest() {
        //given
        var exhibitionId = 5L;

        //when
        var actual = exhibitionService.getByIdWithParticipatedProducts(exhibitionId);

        //then
        assertAll(() -> {
            assertEquals(exhibitionId, actual.getExhibitionId());
            assertEquals("Art for the Millions: American Culture and Politics in the 1930s", actual.getTitle());
            assertEquals("The 1930s was a decade of political and social upheaval in the United States,",
                    actual.getSubHeading());
            assertEquals("MET", actual.getLocation());
            assertEquals(LocalDate.parse("2023-09-07"), actual.getStartDate());
            assertEquals(LocalDate.parse("2023-12-10"), actual.getEndDate());
            assertEquals(ExhibitionStatus.end, actual.getStatus());
            assertEquals("1930s.jpg", actual.getPoster());
            assertThat(actual.getProducts())
                    .hasSize(0);
        });
    }

    @Test
    @DisplayName("존재하지 않는 전시 ID로 전시 상세 조회 시 예외 발생하는 테스트")
    void getByIdWithParticipatedProductsWithNotExistsIdTest() {
        //given
        var notExistsExhibitionId = 100L;

        //expected
        assertThrows(SuperpositionAdminException.class,
                () -> exhibitionService.getByIdWithParticipatedProducts(notExistsExhibitionId));
    }

    @Test
    @DisplayName("전시 생성 테스트")
    void createTest() {
        //given
        var req = RequestCreateExhibition.builder()
                .title("제목입니다.")
                .subHeading("부제목입니다.")
                .startDate(LocalDate.parse("2024-01-01"))
                .endDate(LocalDate.parse("2024-05-01"))
                .location("장소입니다.")
                .status(ExhibitionStatus.current.name())
                .productIds(Set.of(1L, 2L, 3L))
                .build();
        var poster = new MockMultipartFile("file", "test.txt", "text/plain",
                "test file".getBytes(StandardCharsets.UTF_8));

        //when
        var createdExhibitionId = exhibitionService.create(req, poster);

        //then
        var actual = exhibitionMapper.findById(createdExhibitionId).get();
        var productExhibitions = productExhibitionMapper.findByExhibitionId(createdExhibitionId);
        assertAll(() -> {
            assertEquals(req.getTitle(), actual.getTitle());
            assertEquals(req.getSubHeading(), actual.getSubHeading());
            assertEquals(req.getLocation(), actual.getLocation());
            assertEquals(req.getStartDate(), actual.getStartDate());
            assertEquals(req.getEndDate(), actual.getEndDate());
            assertEquals(ExhibitionStatus.findByName(req.getStatus()), actual.getStatus());
            assertEquals(poster.getName(), actual.getPoster());
            assertThat(productExhibitions)
                    .hasSize(3)
                    .extracting("productId", "exhibitionId")
                    .containsExactlyInAnyOrder(
                            tuple(1L, createdExhibitionId),
                            tuple(2L, createdExhibitionId),
                            tuple(3L, createdExhibitionId)
                    );
        });
    }

    @Test
    @DisplayName("전시 수정 테스트")
    void updateTest() {
        //given
        var createReq = RequestCreateExhibition.builder()
                .title("제목입니다.")
                .subHeading("부제목입니다.")
                .startDate(LocalDate.parse("2024-01-01"))
                .endDate(LocalDate.parse("2024-05-01"))
                .location("장소입니다.")
                .status(ExhibitionStatus.current.name())
                .productIds(Set.of(1L, 2L, 3L))
                .build();
        var createPoster = new MockMultipartFile("file", "test.txt", "text/plain",
                "test file".getBytes(StandardCharsets.UTF_8));
        var createdExhibitionId = exhibitionService.create(createReq, createPoster);

        var req = RequestUpdateExhibition.builder()
                .title("제목입니다.-edit")
                .subHeading("부제목입니다.-edit")
                .startDate(LocalDate.parse("2024-01-02"))
                .endDate(LocalDate.parse("2024-05-02"))
                .location("장소입니다.-edit")
                .status(ExhibitionStatus.upcoming)
                .oldPoster(createPoster.getName())
                .productIds(Set.of(4L, 5L))
                .build();
        var poster = new MockMultipartFile("file-edit", "test.txt", "text/plain",
                "test file".getBytes(StandardCharsets.UTF_8));

        //when
        exhibitionService.update(createdExhibitionId, req, poster);

        //then
        var actual = exhibitionMapper.findById(createdExhibitionId).get();
        var productExhibitions = productExhibitionMapper.findByExhibitionId(createdExhibitionId);
        assertAll(() -> {
            assertEquals(req.getTitle(), actual.getTitle());
            assertEquals(req.getSubHeading(), actual.getSubHeading());
            assertEquals(req.getLocation(), actual.getLocation());
            assertEquals(req.getStartDate(), actual.getStartDate());
            assertEquals(req.getEndDate(), actual.getEndDate());
            assertEquals(req.getStatus(), actual.getStatus());
            assertEquals(poster.getName(), actual.getPoster());
            assertThat(productExhibitions)
                    .hasSize(2)
                    .extracting("productId", "exhibitionId")
                    .containsExactlyInAnyOrder(
                            tuple(4L, createdExhibitionId),
                            tuple(5L, createdExhibitionId)
                    );
        });
    }

    @Test
    @DisplayName("전시 수정 시 이미지를 첨부하지 않으면 기존 이미지를 그대로 사용하며, 작품 ID가 비어있다면 작품 등록을 하지 않는 테스트")
    void updateWithOldPictureAndEmptyProductIdsTest() {
        //given
        var createReq = RequestCreateExhibition.builder()
                .title("제목입니다.")
                .subHeading("부제목입니다.")
                .startDate(LocalDate.parse("2024-01-01"))
                .endDate(LocalDate.parse("2024-05-01"))
                .location("장소입니다.")
                .status(ExhibitionStatus.current.name())
                .productIds(Set.of(1L, 2L, 3L))
                .build();
        var createPoster = new MockMultipartFile("file", "test.txt", "text/plain",
                "test file".getBytes(StandardCharsets.UTF_8));
        var createdExhibitionId = exhibitionService.create(createReq, createPoster);

        var req = RequestUpdateExhibition.builder()
                .title("제목입니다.-edit")
                .subHeading("부제목입니다.-edit")
                .startDate(LocalDate.parse("2024-01-02"))
                .endDate(LocalDate.parse("2024-05-02"))
                .location("장소입니다.-edit")
                .status(ExhibitionStatus.upcoming)
                .oldPoster(createPoster.getName())
                .productIds(Set.of())
                .build();

        //when
        exhibitionService.update(createdExhibitionId, req, null);

        //then
        var actual = exhibitionMapper.findById(createdExhibitionId).get();
        var productExhibitions = productExhibitionMapper.findByExhibitionId(createdExhibitionId);
        assertAll(() -> {
            assertEquals(req.getTitle(), actual.getTitle());
            assertEquals(req.getSubHeading(), actual.getSubHeading());
            assertEquals(req.getLocation(), actual.getLocation());
            assertEquals(req.getStartDate(), actual.getStartDate());
            assertEquals(req.getEndDate(), actual.getEndDate());
            assertEquals(req.getStatus(), actual.getStatus());
            assertEquals(req.getOldPoster(), actual.getPoster());
            assertThat(productExhibitions).isEmpty();
        });
    }

    private Tuple id1Exhibition() {
        return tuple(
                "Christmas Tree and Neapolitan Baroque Crèche",
                "The Met continues a longstanding holiday tradition with the presentation of its Christmas tree.",
                "MET",
                LocalDate.parse("2023-11-21"),
                LocalDate.parse("2024-01-07"),
                ExhibitionStatus.end.getValue(),
                1,
                Set.of("문소", "삼이공", "윤지호")
        );
    }

    private Tuple id2Exhibition() {
        return tuple(
                "Proof: Maxime Du Camp’s Photographs of the Eastern Mediterranean and North Africa",
                "Officially encouraged to exploit photography’s “uncontestable exactitude,",
                "MET",
                LocalDate.parse("2023-10-23"),
                LocalDate.parse("2024-01-21"),
                ExhibitionStatus.end.getValue(),
                1,
                Set.of("문소"));
    }

    private Tuple id3Exhibition() {
        return tuple(
                "Vertigo of Color: Matisse, Derain, and the Origins of Fauvism",
                "The exhibition is made possible by The Florence Gould Foundation.",
                "MET",
                LocalDate.parse("2023-10-13"),
                LocalDate.parse("2024-01-21"),
                ExhibitionStatus.end.getValue(),
                1,
                Set.of("문소"));
    }

    private Tuple id4Exhibition() {
        return tuple(
                "Picasso: A Cubist Commission in Brooklyn",
                "In 1910 Pablo Picasso (1881–1973) embarked on a decorative commission for the Brooklyn residence of artist",
                "MET",
                LocalDate.parse("2023-09-14"),
                LocalDate.parse("2024-01-14"),
                ExhibitionStatus.end.getValue(),
                1,
                Set.of("삼이공"));
    }

    private Tuple id5Exhibition() {
        return tuple(
                "Art for the Millions: American Culture and Politics in the 1930s",
                "The 1930s was a decade of political and social upheaval in the United States,",
                "MET",
                LocalDate.parse("2023-09-07"),
                LocalDate.parse("2023-12-10"),
                ExhibitionStatus.end.getValue(),
                1,
                Set.of());
    }

    private Tuple id6Exhibition() {
        return tuple(
                "Van Gogh’s Cypresses",
                "Van Gogh’s Cypresses is the first exhibition to focus on the trees",
                "MET",
                LocalDate.parse("2023-05-22"),
                LocalDate.parse("2023-08-27"),
                ExhibitionStatus.end.getValue(),
                1,
                Set.of());
    }

    private Tuple id7Exhibition() {
        return tuple(
                "Lineages: Korean Art at The Met",
                "METXSuperposition 겨울 전시",
                "MET",
                LocalDate.parse("2023-12-16"),
                LocalDate.parse("2023-12-25"),
                ExhibitionStatus.current.getValue(),
                1,
                Set.of());
    }

    private Tuple id8Exhibition() {
        return tuple(
                "The Facade Commission: Nairy Baghramian, Scratching the Back",
                "For The Met Fifth Avenue’s facade niches,",
                "MET",
                LocalDate.parse("2023-12-16"),
                LocalDate.parse("2024-05-28"),
                ExhibitionStatus.current.getValue(),
                1,
                Set.of());
    }

    private Tuple id9Exhibition() {
        return tuple(
                "Grounded in Clay: The Spirit of Pueblo Pottery",
                "Pueblo Indian pottery embodies four main natural elements",
                "MET",
                LocalDate.parse("2023-12-16"),
                LocalDate.parse("2024-06-04"),
                ExhibitionStatus.current.getValue(),
                1,
                Set.of());
    }

    private Tuple id10Exhibition() {
        return tuple(
                "여기는 따뜻해",
                "성수지앵XSuperposition 겨울 전시",
                "성수지앵",
                LocalDate.parse("2023-12-16"),
                LocalDate.parse("2023-12-25"),
                ExhibitionStatus.current.getValue(),
                1,
                Set.of());
    }

    private Tuple id14Exhibition() {
        return tuple(
                "Africa & Byzantium",
                "The exhibition is made possible by the Ford Foundation, The Giorgi Family Foundation, and Mary Jaharis.",
                "MET",
                LocalDate.parse("2023-12-16"),
                LocalDate.parse("2024-03-03"),
                ExhibitionStatus.current.getValue(),
                1,
                Set.of("문소"));
    }

    private Tuple id15Exhibition() {
        return tuple(
                "Women Dressing Women",
                "The exhibition and catalogue are made possible by Morgan Stanley.",
                "MET",
                LocalDate.parse("2023-12-16"),
                LocalDate.parse("2024-03-03"),
                ExhibitionStatus.upcoming.getValue(),
                1,
                Set.of("문소")
        );
    }
}