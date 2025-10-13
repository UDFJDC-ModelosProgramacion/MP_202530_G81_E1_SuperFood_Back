import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import jakarta.transaction.Transactional;
import lombok.Data;

@DataJpaTest
@Transactional
@Import(FotoService.class)
public class FotoServiceTest {
    @Autowired
    private FotoService fotoService;
    
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private List<FotoEntity> fotoList = new ArrayList<>();

        
}
