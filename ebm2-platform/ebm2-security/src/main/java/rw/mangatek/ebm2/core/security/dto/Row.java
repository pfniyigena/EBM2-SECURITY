package rw.mangatek.ebm2.core.security.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Row {
    private String table;
    private String actionCd;
    private String tin;
    private String mrcno;
    private String intkey;
    private String signkey;
    private String commkey;
    private String token;
    private String nonVat;
    private String trsmTaxFlg;

    // Getters and Setters
}
