package com.mrf.mrfmaharashtra.Model;

public class SopsModel {
    private  String sopsId;
    private  String subCategoryId;
    private  String sopsName;
    private  String pdf_content;




    public SopsModel(String sopsId,String subCategoryId,String sopsName,String pdf_content) {
        this.sopsId = sopsId;
        this.subCategoryId = subCategoryId;
        this.sopsName = sopsName;
        this.pdf_content = pdf_content;

    }
    public SopsModel() {
    }

    public String getSopsId() {
        return sopsId;
    }

    public void setSopsId(String sopsId) {
        this.sopsId = sopsId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSopsName() {
        return sopsName;
    }

    public void setSopsName(String sopsName) {
        this.sopsName = sopsName;
    }

    public String getPdf_content() {
        return pdf_content;
    }

    public void setPdf_content(String pdf_content) {
        this.pdf_content = pdf_content;
    }
}
