package org.gy.framework.biz;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.Verifications;

import org.apache.ibatis.session.SqlSession;
import org.gy.framework.dao.SysResourceMapper;
import org.gy.framework.model.SysResource;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class SysResourceBizTest {

    @Tested
    private SysResourceBiz sysResourceBiz;
    @Injectable
    protected SqlSession   sqlSessionMaster;
    @Injectable
    protected SqlSession   sqlSessionSlave;

    @Test
    public void testInsertSelective() {

        final SysResourceMapper mapper = new MockUp<SysResourceMapper>(SysResourceMapper.class) {
            @Mock
            int insertSelective(SysResource record) {
                return 1;
            }
        }.getMockInstance();

        new Expectations() {
            {
                sqlSessionMaster.getMapper(withAny(SysResourceMapper.class));
                result = mapper;
                times = 1;
            }
        };
        SysResource entity = new SysResource();
        entity.setId(1L);
        Long actual = sysResourceBiz.insertSelective(entity);
        Assert.assertEquals(actual.longValue(), 1l);

        new Verifications() {
            {
                sqlSessionMaster.getMapper(withAny(SysResourceMapper.class));
                times = 1;
            }
        };
    }
}
