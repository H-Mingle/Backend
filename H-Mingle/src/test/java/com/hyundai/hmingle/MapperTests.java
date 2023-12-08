package com.hyundai.hmingle;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.ChannelMapper;
import com.hyundai.hmingle.mapper.ImageMapper;
import com.hyundai.hmingle.mapper.ChannelMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MapperTests {
	 private static final Logger log = Logger.getLogger(MapperTests.class);

	    @Autowired
	    private ChannelMapper mapper;

	    @Autowired
	    private ImageMapper mapper2;
	    
	    @Test
	    public void testGetList() {
	        System.out.println(mapper.getList());
	    }
	    
	    @Test
	    public void testSaveFile() {
	    	List<ImageCreateRequest> images = new ArrayList<>();
	    	ImageCreateRequest imageRequest = new ImageCreateRequest("테스트.txt", "abc.txt", 10768);
	    	imageRequest.setPostId(1L);
	    	images.add(imageRequest);
	    	
	    	ImageCreateRequest imageRequest2 = new ImageCreateRequest("테스트2.txt", "abc2.txt", 10768);
	    	imageRequest2.setPostId(1L);
	    	images.add(imageRequest2);
	    	
	    	ImageCreateRequest imageRequest3 = new ImageCreateRequest("테스트2.txt", "abc2.txt", 10768);
	    	imageRequest3.setPostId(1L);
	    	images.add(imageRequest3);
	    	
	    	
	    	log.info("id="+images.get(0).getPostId()+"sequence="+images.get(0).getSequence()+"saveName="+images.get(0).getSaveName());
	    	log.info("id="+images.get(1).getPostId()+"sequence="+images.get(1).getSequence()+"saveName="+images.get(1).getSaveName());
	    	mapper2.saveAll(images);
	    }

}
