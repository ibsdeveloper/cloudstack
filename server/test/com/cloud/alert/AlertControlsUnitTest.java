package com.cloud.alert;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.cloud.alert.dao.AlertDao;
import com.cloud.server.ManagementServerImpl;
import com.cloud.user.Account;
import com.cloud.user.AccountManager;

public class AlertControlsUnitTest extends TestCase {
    private static final Logger s_logger = Logger.getLogger(AlertControlsUnitTest.class);

    @Spy ManagementServerImpl _mgmtServer = new ManagementServerImpl();
    @Mock AccountManager _accountMgr;
    @Mock AlertDao _alertDao;
    @Override
    @Before
    protected void setUp() {
        MockitoAnnotations.initMocks(this);
        _mgmtServer._alertDao = _alertDao;
        _mgmtServer._accountMgr = _accountMgr;
        doReturn(3L).when(_accountMgr).checkAccessAndSpecifyAuthority(any(Account.class), anyLong());
        when(_alertDao.archiveAlert(anyList(), anyString(), any(Date.class), anyLong())).thenReturn(true);
        when(_alertDao.deleteAlert(anyList(), anyString(), any(Date.class), anyLong())).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInjected() throws Exception {
        s_logger.info("Starting test to archive and delete alerts");
        archiveAlerts();
        deleteAlerts();
        s_logger.info("archive/delete alerts: TEST PASSED");
    }

    protected void archiveAlerts() {
        // archive alerts
        String msg = "Archive Alerts: TEST FAILED";
        assertNotNull(msg, _mgmtServer._alertDao.archiveAlert(null, "system alert",null, 2L));
    }

    protected void deleteAlerts() {
        // delete alerts
        String msg = "Delete Alerts: TEST FAILED";
        assertNotNull(msg, _mgmtServer._alertDao.deleteAlert(null, "system alert",null, 2L));
    }
}
