package com.hyundai.hmingle;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hyundai.hmingle.mapper.ChannelMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MapperTests {
	 private static final Logger log = Logger.getLogger(MapperTests.class);

	    @Autowired
	    private ChannelMapper mapper;
	    
	    @Test
	    public void testGetList() {
	        System.out.println(mapper.getList());
	    }
}
