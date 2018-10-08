package id.ac.tazkia.akademik.aplikasiakademik.controller;

import id.ac.tazkia.akademik.aplikasiakademik.dao.KaryawanDao;
import id.ac.tazkia.akademik.aplikasiakademik.entity.Karyawan;
import id.ac.tazkia.akademik.aplikasiakademik.entity.StatusRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KaryawanController {


    @Autowired
    private KaryawanDao karyawanDao;

    @GetMapping("/karyawan/list")
    public void daftarKaryawan(Model model, @PageableDefault(size = 10) Pageable page, String search){

        if (StringUtils.hasText(search)) {
            model.addAttribute("search", search);
            model.addAttribute("daftarKaryawan", karyawanDao.findByStatusAndNamaKaryawanContainingIgnoreCaseOrderByNamaKaryawan(StatusRecord.AKTIF, search, page));
        } else {
            model.addAttribute("daftarkaryawan", karyawanDao.findByStatus(StatusRecord.AKTIF,page));

        }


    }



    @GetMapping("/karyawan/form")
    public void  formKaryawan(){
    }
}
