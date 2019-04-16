package id.ac.tazkia.akademik.aplikasiakademik.dao;

import id.ac.tazkia.akademik.aplikasiakademik.entity.Jadwal;
import id.ac.tazkia.akademik.aplikasiakademik.entity.PresensiDosen;
import id.ac.tazkia.akademik.aplikasiakademik.entity.StatusRecord;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PresensiDosenDao extends PagingAndSortingRepository<PresensiDosen, String> {
    List<PresensiDosen> findByStatusAndJadwal(StatusRecord status, Jadwal jadwal);
}
