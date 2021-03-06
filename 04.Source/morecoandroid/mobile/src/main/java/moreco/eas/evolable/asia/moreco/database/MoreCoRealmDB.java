package moreco.eas.evolable.asia.moreco.database;

/**
 * Created by PhanVanTrung on 2016/07/02.
 */

import android.content.Context;

import java.security.spec.ECField;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by PhanVanTrung on 2016/06/07.
 */
public class MoreCoRealmDB {

    private Realm mRealm;

    public MoreCoRealmDB(Context context) {
        /// Create a RealmConfiguration that saves the Realm file in the app's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);

        // Get a Realm instance for this thread
        mRealm = Realm.getInstance(realmConfig);
    }

    //DictVersion DB
    public void writetoDictVersionDB(String version, int versionId) {
       try{
           mRealm.beginTransaction();

        // Create a new object
        DictVersionDataModel dictDataModel = mRealm.createObject(DictVersionDataModel.class);
        dictDataModel.setVersion(version);
        dictDataModel.setVersionId(versionId);
        mRealm.commitTransaction();
       } catch (Exception e){
           mRealm.close();
       }

    }

    //DictLanguages DB
    public void writetoDictLanguagesDB(int id, String languageCode, int dataStatus) {
        try {
            mRealm.beginTransaction();
            // Create a new object
            DictLanguagesDataModel dictLangDataModel = mRealm.createObject(DictLanguagesDataModel.class);
            dictLangDataModel.setId(id);
            dictLangDataModel.setCode(languageCode);
            dictLangDataModel.setStatus(dataStatus);
            mRealm.commitTransaction();
        } catch (Exception e){
            mRealm.close();
        }
    }

    //DictLanguagesName DB
    public void writetoDictLanguagesNameDB(int id, int dictLanguageId, int inLanguageId, String name, int dataStatus) {
       try {
           mRealm.beginTransaction();
           // Create a new object
           DictLangNameDataModel dictLangNameDataModel = mRealm.createObject(DictLangNameDataModel.class);
           dictLangNameDataModel.setId(id);
           dictLangNameDataModel.setdictLanguageId(dictLanguageId);
           dictLangNameDataModel.setInLanguageId(inLanguageId);
           dictLangNameDataModel.setName(name);
           dictLangNameDataModel.setDataSatus(dataStatus);
           mRealm.commitTransaction();
       } catch (Exception e){
           mRealm.close();
       }
    }


    //DictSentence DB
    public void writetoDictSentenceDB(int id,int dataStatus) {
        try {
            mRealm.beginTransaction();
            // Create a new object
            DictSentenceDataModel dictSentence = mRealm.createObject(DictSentenceDataModel.class);
            dictSentence.setSentenceId(id);
            dictSentence.setData_status(dataStatus);
            mRealm.commitTransaction();
        }catch (Exception e){
            mRealm.close();
        }
    }

    //DictSentenceTranslation DB
    public void writetoDictSentenceTranslationDB(int id, int dictLangId, int dicSenId, String translatedsentence,String searchtext, int dataStatus) {
      try {
        mRealm.beginTransaction();
        // Create a new object
        DictSentenceTranslationDataModel dictSentenceTrans  = mRealm.createObject(DictSentenceTranslationDataModel.class);
        dictSentenceTrans.setId(id);
        dictSentenceTrans.setDictlanguageid(dictLangId);
        dictSentenceTrans.setDictsentenceid(dicSenId);
        dictSentenceTrans.setTranslated_sentence(translatedsentence);
        dictSentenceTrans.setSearching_text(searchtext);
        dictSentenceTrans.setData_status(dataStatus);
        mRealm.commitTransaction();
      } catch (Exception e){
          mRealm.close();
      }
    }

    public RealmResults<DictSentenceTranslationDataModel> queryAllRealmDB() {
        try {
            RealmQuery<DictSentenceTranslationDataModel> query = mRealm.where(DictSentenceTranslationDataModel.class);
            RealmResults<DictSentenceTranslationDataModel> resultAll = query.findAll();
            return resultAll;
        }catch (Exception e){
            mRealm.close();
            return null;
        }
    }

//    public DictVersionDataModel getModelfromRealmDB(int versionId) {
//        RealmQuery<DictVersionDataModel> query = mRealm.where(DictVersionDataModel.class);
//        query.equalTo("versionId", versionId);
//        RealmResults<DictVersionDataModel> result = query.findAll();
//        if (result == null) {
//            return null;
//        }
//        return result.get(1);
//    }
}
