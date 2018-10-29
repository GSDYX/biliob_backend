package com.jannchie.biliob.service.serviceImpl;

import com.jannchie.biliob.exception.UserAlreadyFavoriteVideoException;
import com.jannchie.biliob.exception.VideoAlreadyFocusedException;
import com.jannchie.biliob.model.User;
import com.jannchie.biliob.model.Video;
import com.jannchie.biliob.repository.UserRepository;
import com.jannchie.biliob.repository.VideoRepository;
import com.jannchie.biliob.service.UserService;
import com.jannchie.biliob.service.VideoService;
import com.jannchie.biliob.utils.LoginCheck;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author jannchie
 */
@Service
public class VideoServiceImpl implements VideoService {
    private static final Logger logger = LogManager.getLogger(VideoServiceImpl.class);
    private final VideoRepository respository;
    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public VideoServiceImpl(VideoRepository respository, UserRepository userRepository, UserService userService) {
        this.respository = respository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Video getVideoDetails(Long aid) {
        return respository.findByAid(aid);
    }

    @Override
    public Video postVideoByAid(Long aid) throws UserAlreadyFavoriteVideoException, VideoAlreadyFocusedException {
        User user = userService.addFavoriteVideo(aid);
        logger.info(aid);
        logger.info(user.getName());
        if (respository.findByAid(aid) != null) {
            throw new VideoAlreadyFocusedException(aid);
        }
        return respository.save(new Video(aid));
    }

    @Override
    public Page<Video> getVideo(Long aid, String text, Integer page, Integer pagesize) {
        if (!(aid == -1)) {
            logger.info("[GET]searchByAid");
            return respository.searchByAid(aid, PageRequest.of(page, pagesize, new Sort(Sort.Direction.DESC, "data.0.view")));
        } else if (!Objects.equals(text, "")) {
            logger.info("[GET]searchByText");
            return respository.searchByText(text, PageRequest.of(page, pagesize, new Sort(Sort.Direction.DESC, "data.0.view")));
        } else {

<<<<<<<Updated upstream
            logger.info("[GET]findAll");
//            Query query=new Query(Criteria.where("aid").ne(null));
//            List<Video> v =  mongoTemplate.find(query , Video.class);
//            System.out.println(v.getAuthor());
//            Pageable pageable = new PageRequests(page, size);
//            Query query = new Query().with(pageable);
//            List<Video> list = mongoTemplate.find(query, Video.class);
//            return PageableExecutionUtils.getPage(
//                    list,
//                    pageable,
//                    () -> mongoTemplate.count(query, XXX.class));
=======
            logger.info("获取全部视频数据");
>>>>>>>Stashed changes
            return respository.findByDataIsNotNull(PageRequest.of(page, pagesize, new Sort(Sort.Direction.DESC, "data.0.view")));
        }
    }

    @Override
    public Slice<Video> getAuthorVideo(Long aid, Long mid, Integer page, Integer pagesize) {
        return respository.findAuthorOtherVideo(aid, mid, PageRequest.of(page, pagesize, new Sort(Sort.Direction.DESC, "data.0.view")));
    }
}