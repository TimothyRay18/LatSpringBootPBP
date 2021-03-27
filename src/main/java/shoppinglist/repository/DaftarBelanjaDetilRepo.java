/*
 * DaftarBelanjaRepo.java
 *
 * Created on Mar 27, 2021, 13.45
 */
package shoppinglist.repository;

        import org.springframework.data.jpa.repository.JpaRepository;
        import shoppinglist.entity.DaftarBelanjaDetil;


/**
 * @author timothy
 */
public interface DaftarBelanjaDetilRepo extends JpaRepository<DaftarBelanjaDetil, Long>
{
}
