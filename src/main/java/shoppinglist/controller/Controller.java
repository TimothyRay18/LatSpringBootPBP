/*
 * Controller.java
 *
 * Created on Mar 26, 2021, 20.22
 */
package shoppinglist.controller;

import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaDetilRepo;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


/**
 * @author timothy
 */

public class Controller {
    public static void cariDaftarBelanja(DaftarBelanjaRepo repo, Scanner keyb) {
        System.out.print("Masukkan nama dari objek DaftarBelanja yg ingin ditampilkan: ");
        String judul = keyb.nextLine();
        System.out.println("Hasil pencarian: ");
        List<DaftarBelanja> allByJudul = repo.findByJudulIgnoreCaseContaining(judul);
        if(!allByJudul.isEmpty()) {
            for (DaftarBelanja db : allByJudul) {
                System.out.println(db.getJudul());
                List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
                for (DaftarBelanjaDetil barang : listBarang) {
                    System.out.println("\t" + barang.getNamaBarang() + " " + barang.getByk() + " " + barang.getSatuan());
                }
            }
        } else {
            System.out.println("DATA TIDAK DITEMUKAN");
        }
    }

    public static void tambahDaftarBelanja(DaftarBelanjaRepo repo, Scanner keyb) {
        System.out.print("Input Judul yang akan ditambahkan(ketik 0 untuk batal): ");
        String judul = keyb.nextLine();
        if(!judul.equals("0")) {
            DaftarBelanja daftarBelanja = new DaftarBelanja();
            daftarBelanja.setJudul(judul);
            daftarBelanja.setTanggal(LocalDateTime.now());
            repo.save(daftarBelanja);
            System.out.println("Daftar Belanja dengan judul " + judul + " berhasil ditambahkan");
            System.out.println("Input jumlah detil daftar belanja: ");
            int jml = keyb.nextInt();
            keyb.nextLine();
            List<DaftarBelanjaDetil> listBelanja = new LinkedList<>();
            for(int i=0;i<jml;i++) {
                DaftarBelanjaDetil detil = new DaftarBelanjaDetil();
                detil.setId(daftarBelanja.getId(),i + 1);
                System.out.println("Input nama barang: ");
                String nama = keyb.nextLine();
                detil.setNamaBarang(nama);
                System.out.println("Input jumlah: ");
                long banyak = Long.parseLong(keyb.nextLine());
                detil.setByk(banyak);
                System.out.println("Input satuan: ");
                String satuan = keyb.nextLine();
                detil.setSatuan(satuan);
                System.out.println("Input memo: ");
                String memo = keyb.nextLine();
                detil.setMemo(memo);
                listBelanja.add(detil);
            }
            daftarBelanja.setDaftarBarang(listBelanja);
            repo.save(daftarBelanja);
            System.out.println("Menambah detil belanja berhasil");
        } else {
            System.out.println("Menambah daftar belanja dibatalkan");
        }
    }

    public static void hapusDaftarBelanja(DaftarBelanjaRepo repo, DaftarBelanjaDetilRepo repoDetil, Scanner keyb) {
        System.out.print("Input id daftar belanja yang akan dihapus(ketik 0 untuk batal): ");
        long id = Integer.parseInt(keyb.nextLine());
        if(id != 0) {
            Optional<DaftarBelanja> optDB = repo.findById(id);
            if (optDB.isPresent()) {
                DaftarBelanja daftarBelanja = optDB.get();
                System.out.println("Daftar belanja dengan judul " + repo.findById(id).get().getJudul() + " telah dihapus");
                repoDetil.deleteByDaftarBelanja_id(daftarBelanja.getId());
                repo.deleteById(id);
            } else {
                System.out.println("\tTIDAK DITEMUKAN.");
            }
        } else {
            System.out.println("Hapus daftar belanja dibatalkan");
        }
    }
}
