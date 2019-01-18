package com.dynamic.config;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

/**
 * 自定义实现动态数据源
 */
public class DynamicDataSource implements DataSource {

    private final AtomicReference<DataSource> dataSourceAtomicReference;

    public DynamicDataSource(DataSource dataSource){
        dataSourceAtomicReference = new AtomicReference<>(dataSource);
    }

    /**
     * 设置新的数据源并返回旧数据源
     * @param newDataSource     新数据源
     * @return                  旧数据源
     */
    public DataSource setDataSource(DataSource newDataSource){
        return dataSourceAtomicReference.getAndSet(newDataSource);
    }


    @Override
    public Connection getConnection() throws SQLException {
        return dataSourceAtomicReference.get().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSourceAtomicReference.get().getConnection(username,password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSourceAtomicReference.get().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSourceAtomicReference.get().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSourceAtomicReference.get().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSourceAtomicReference.get().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSourceAtomicReference.get().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSourceAtomicReference.get().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSourceAtomicReference.get().getParentLogger();
    }


}
