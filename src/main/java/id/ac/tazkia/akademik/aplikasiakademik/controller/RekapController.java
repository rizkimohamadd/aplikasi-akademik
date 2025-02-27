package id.ac.tazkia.akademik.aplikasiakademik.controller;

import id.ac.tazkia.akademik.aplikasiakademik.dao.*;
import id.ac.tazkia.akademik.aplikasiakademik.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RekapController {
    @Autowired
    private KrsDetailDao krsDetailDao;
    @Autowired
    private KrsDao krsDao;
    @Autowired
    private TahunAkademikDao tahunAkademikDao;
    @Autowired
    private MahasiswaDao mahasiswaDao;
    @Autowired
    private JadwalDao jadwalDao;

    @ModelAttribute("angkatan")
    public Iterable<Mahasiswa> angkatan() {
        return mahasiswaDao.cariAngkatan();
    }

    @ModelAttribute("tahun")
    public Iterable<TahunAkademik> tahun() {
        return tahunAkademikDao.findByStatusNotInOrderByTahunDesc(StatusRecord.HAPUS);
    }

    @GetMapping("/rekap/aktifasi")
    public void rekapAktifasi(Model model, @RequestParam(required = false) TahunAkademik tahunAkademik,
                              @RequestParam(required = false) Jadwal matkul,@PageableDefault(size = 10) Pageable pageable) {




        if (tahunAkademik != null) {
            List<Krs> krs = krsDao.findByTahunAkademikAndStatusAndKrsDetailsNotNull(tahunAkademik,StatusRecord.AKTIF);
            List<Jadwal> jadwal = jadwalDao.findByTahunAkademikAndHariNotNull(tahunAkademik);
            model.addAttribute("matkul", jadwal);

            List<Krs> krsWithoutDuplicates = krs.stream()
                    .distinct()
                    .collect(Collectors.toList());

            //        convert list to page
            long start = pageable.getOffset();
            int mulai = (int) start;
            long end = (start + pageable.getPageSize()) > krsWithoutDuplicates.size() ? krsWithoutDuplicates.size() : (start + pageable.getPageSize());
            int selesai = (int) end;
            Page<Krs> pages = new PageImpl<Krs>(krsWithoutDuplicates.subList(mulai,selesai), pageable, krs.size());

//            Page<Krs> krsPage = new PageImpl<>(krsWithoutDuplicates, pageable, krs.size());


            model.addAttribute("selectedTahun", tahunAkademik);
            model.addAttribute("krs",pages);
            if (matkul != null){
                Page<KrsDetail> krsDetail = krsDetailDao.findByJadwalAndStatus(matkul,StatusRecord.AKTIF,pageable);
                model.addAttribute("selectedTahun", tahunAkademik);
                model.addAttribute("krs",krsDetail);
                model.addAttribute("selected",matkul);
            }

        }

    }
}