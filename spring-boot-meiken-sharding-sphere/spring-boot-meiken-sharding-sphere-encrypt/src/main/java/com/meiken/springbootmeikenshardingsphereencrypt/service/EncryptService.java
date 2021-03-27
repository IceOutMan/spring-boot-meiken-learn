package com.meiken.springbootmeikenshardingsphereencrypt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meiken.springbootmeikenshardingsphereencrypt.util.SensitiveEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author glf
 * @Date 2020/9/28
 */
@Service
public class EncryptService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private String ENCRYPT_JSON_KEY = "encrypt";
    private String ENCRYPT_JSON_TABLE = "table";
    private String ENCRYPT_JSON_TABLE_KEY_ID = "tableKeyId";
    private String ENCRYPT_JSON_TABLE_KEY_TYPE = "tableKeyType";
    private String ENCRYPT_JSON_TABLE_QUERY_ENCRYPT_COLUMN = "tableQueryEncryptColumn";
    private String ENCRYPT_JSON_COLUMNS = "columns";
    private String ENCRYPT_JSON_PLAIN_COLUMN = "plainColumn";
    private String ENCRYPT_JSON_ENCRYPT_COLUMN = "encryptColumn";

    private String ENCRYPT_JSON_TABLE_KEY_TYPE_INT = "INT";
    private String ENCRYPT_JSON_TABLE_KEY_TYPE_STRING = "STRING";


    @Autowired
    private EntityManager entityManager;

    @Transactional(rollbackFor = Exception.class)
    public void encrypt(JSONObject encryptJsonObject) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {

        String encrypt = encryptJsonObject.getString(ENCRYPT_JSON_KEY);
        String tableName = encryptJsonObject.getString(ENCRYPT_JSON_TABLE);
        String tableKeyId = encryptJsonObject.getString(ENCRYPT_JSON_TABLE_KEY_ID);
        String tableKeyType = encryptJsonObject.getString(ENCRYPT_JSON_TABLE_KEY_TYPE);
        String tableQueryEncryptColumn = encryptJsonObject.getString(ENCRYPT_JSON_TABLE_QUERY_ENCRYPT_COLUMN);
        JSONArray columnsJSONArray = encryptJsonObject.getJSONArray(ENCRYPT_JSON_COLUMNS);

        if (StringUtils.isEmpty(tableKeyId)) {
            tableKeyId = "id";
            tableKeyType = ENCRYPT_JSON_TABLE_KEY_TYPE_INT;
        }

        logger.info("「数据脱敏」：TABLE-{}", JSON.toJSONString(tableName));

        List<Object> keyIdList = queryIdLisFromTable(tableName, tableKeyId, tableQueryEncryptColumn);

        List<ColumnData> columnDataList = new ArrayList<>();
        for (int k = 0; k < columnsJSONArray.size(); k++) {
            JSONObject column = columnsJSONArray.getJSONObject(k);
            String plainColumn = column.getString(ENCRYPT_JSON_PLAIN_COLUMN);
            String encryptColumn = column.getString(ENCRYPT_JSON_ENCRYPT_COLUMN);

            logger.info("「数据脱敏」：COLUMN-{}", JSON.toJSONString(plainColumn));

            ColumnData columnData = new ColumnData();
            columnData.setPlainColumn(plainColumn);
            columnData.setEncryptColumn(encryptColumn);
            columnData.setIndex(k);

            columnDataList.add(columnData);
        }

        for (int j = 0; j < keyIdList.size(); j++) {

            String keyId = keyIdList.get(j).toString();

            Object[] queryResult = queryPlainColumnData(tableName, tableKeyId, columnDataList, keyId, tableKeyType);
            setEncryptContent(queryResult, columnDataList);

            setData(tableName, tableKeyId, columnDataList, keyId, tableKeyType);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void decrypt(JSONObject encryptJsonObject) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {


        String encrypt = encryptJsonObject.getString(ENCRYPT_JSON_KEY);
        String tableName = encryptJsonObject.getString(ENCRYPT_JSON_TABLE);
        String tableKeyId = encryptJsonObject.getString(ENCRYPT_JSON_TABLE_KEY_ID);
        String tableKeyType = encryptJsonObject.getString(ENCRYPT_JSON_TABLE_KEY_TYPE);
        String tableQueryEncryptColumn = encryptJsonObject.getString(ENCRYPT_JSON_TABLE_QUERY_ENCRYPT_COLUMN);
        JSONArray columnsJSONArray = encryptJsonObject.getJSONArray(ENCRYPT_JSON_COLUMNS);

        if (StringUtils.isEmpty(tableKeyId)) {
            tableKeyId = "id";
            tableKeyType = ENCRYPT_JSON_TABLE_KEY_TYPE_INT;
        }

        logger.info("「数据脱敏」：TABLE-{}", JSON.toJSONString(tableName));

        List<Object> keyIdList = queryIdLisFromTable(tableName, tableKeyId, tableQueryEncryptColumn);

        List<ColumnData> columnDataList = new ArrayList<>();
        for (int k = 0; k < columnsJSONArray.size(); k++) {
            JSONObject column = columnsJSONArray.getJSONObject(k);
            String plainColumn = column.getString(ENCRYPT_JSON_PLAIN_COLUMN);
            String encryptColumn = column.getString(ENCRYPT_JSON_ENCRYPT_COLUMN);

            logger.info("「数据脱敏」：COLUMN-{}", JSON.toJSONString(plainColumn));

            ColumnData columnData = new ColumnData();
            columnData.setPlainColumn(plainColumn);
            columnData.setEncryptColumn(encryptColumn);
            columnData.setIndex(k);

            columnDataList.add(columnData);
        }

        for (int j = 0; j < keyIdList.size(); j++) {

            String keyId = keyIdList.get(j).toString();

            Object[] queryResult = queryPlainColumnData(tableName, tableKeyId, columnDataList, keyId, tableKeyType);
            setEncryptContent(queryResult, columnDataList);

            setData(tableName, tableKeyId, columnDataList, keyId, tableKeyType);
        }
    }


    private void setData(String tableName, String tableKeyId, List<ColumnData> columnDataList, String keyId, String tableKeyType) {

        String encryptColumnSqlContent = getEncryptColumnSqlContent(columnDataList);

        String query = "update " + tableName + " set " + encryptColumnSqlContent + " where " + tableKeyId + "=" + keyId + ";";

        if (ENCRYPT_JSON_TABLE_KEY_TYPE_INT.equals(tableKeyType)) {
            query = "update " + tableName + " set " + encryptColumnSqlContent + " where " + tableKeyId + "=" + keyId + ";";
        } else if (ENCRYPT_JSON_TABLE_KEY_TYPE_STRING.equals(tableKeyType)) {
            query = "update " + tableName + " set " + encryptColumnSqlContent + " where " + tableKeyId + "=" + "'" + keyId + "'" + ";";
        }

        logger.info("「数据脱敏」：SET-DATA-{}", JSON.toJSONString(query));


        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.executeUpdate();
    }


    private String getQueryColumnContent(List<ColumnData> columnDataList) {
        StringBuilder queryColumnBuilder = new StringBuilder();
        for (int i = 0; i < columnDataList.size(); i++) {
            ColumnData columnData = columnDataList.get(i);

            if (i == columnDataList.size() - 1) {
                queryColumnBuilder.append(columnData.getPlainColumn());
            } else {
                queryColumnBuilder.append(columnData.getPlainColumn()).append(",");
            }
        }
        return queryColumnBuilder.toString();
    }


    private String getEncryptColumnSqlContent(List<ColumnData> columnDataList) {
        StringBuilder queryColumnBuilder = new StringBuilder();
        for (int i = 0; i < columnDataList.size(); i++) {
            ColumnData columnData = columnDataList.get(i);

            if (i == columnDataList.size() - 1) {
                queryColumnBuilder.append(columnData.getEncryptColumn())
                        .append("=")
                        .append("'").append(columnData.getEncryptContent()).append("'");
            } else {
                queryColumnBuilder.append(columnData.getEncryptColumn())
                        .append("=")
                        .append("'").append(columnData.getEncryptContent()).append("'")
                        .append(",");
            }
        }
        return queryColumnBuilder.toString();
    }


    private Object[] queryPlainColumnData(String tableName, String tableKeyId, List<ColumnData> columnDataList, String keyId, String tableKeyType) {

        String queryColumnContent = getQueryColumnContent(columnDataList);

        String query = "select " + queryColumnContent + " from " + tableName + " where " + tableKeyId + "=" + keyId + ";";
        if (ENCRYPT_JSON_TABLE_KEY_TYPE_INT.equals(tableKeyType)) {
            query = "select " + queryColumnContent + " from " + tableName + " where " + tableKeyId + "=" + keyId + ";";
        } else if (ENCRYPT_JSON_TABLE_KEY_TYPE_STRING.equals(tableKeyType)) {
            query = "select " + queryColumnContent + " from " + tableName + " where " + tableKeyId + "=" + "'" + keyId + "'" + ";";
        }

        Query nativeQuery = entityManager.createNativeQuery(query);
        List list = nativeQuery.getResultList();
        Object[] result = null;

        if (columnDataList.size() == 1) {
            result = new Object[]{list.get(0)};
        } else {
            result = (Object[]) list.get(0);
        }

        return result;
    }

    private void setEncryptContent(Object[] queryResult, List<ColumnData> columnDataList) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        //加密
        for (ColumnData columnData : columnDataList) {
            int index = columnData.getIndex();
            String plainContent = String.valueOf(queryResult[index]);
            if ("null".equals(plainContent)) {
                plainContent = "";
            }
            String encryptContent = SensitiveEncryptUtil.encrypt(plainContent);

            columnData.setPlainContent(plainContent);
            columnData.setEncryptContent(encryptContent);
        }
    }


    private List<Object> queryIdLisFromTable(String tableName, String tableKeyId, String tableQueryEncryptColumn) {

        String query = "select " + tableKeyId + " from " + tableName + " where " + tableQueryEncryptColumn + " is null limit 200000" + ";";

        Query nativeQuery = entityManager.createNativeQuery(query);

        List result = nativeQuery.getResultList();

        return result;
    }


    class ColumnData {
        String plainColumn;
        String encryptColumn;
        String plainContent;
        String encryptContent;
        int index;

        public String getPlainColumn() {
            return plainColumn;
        }

        public void setPlainColumn(String plainColumn) {
            this.plainColumn = plainColumn;
        }

        public String getEncryptColumn() {
            return encryptColumn;
        }

        public void setEncryptColumn(String encryptColumn) {
            this.encryptColumn = encryptColumn;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getPlainContent() {
            return plainContent;
        }

        public void setPlainContent(String plainContent) {
            this.plainContent = plainContent;
        }

        public String getEncryptContent() {
            return encryptContent;
        }

        public void setEncryptContent(String encryptContent) {
            this.encryptContent = encryptContent;
        }
    }

}