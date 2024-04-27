package com.example.appdispensa.dbhelper

enum class DbEnum(val valore: String) {

    TABELLA_UTENTI("Users"),
    COLONNA_ID("id"),
    COLONNA_NOME("nome"),
    COLONNA_EMAIL("email"),
    COLONNA_PASSWORD("password"),
    //--------
    TABELLA_DISPENSA("Dispensa"),
    COLONNA_ID_UTENTE("id_utente"),
    //--------
    TABELLA_PRODOTTI("Prodotti"),
    COLONNA_ID_DISPENSA("id_dispensa"),
    COLONNA_QUANTITA("quantita"),
    //--------
    TABELLA_MACRONUTRIENTI("Macronutrienti"),
    COLONNA_CARBOIDRATI("carboidrati"),
    COLONNA_PROTEINE("proteine"),
    COLONNA_FIBRE("fibre"),
    COLONNA_GRASSI("grassi")

}