package com.team17.preProject.helper.stub;

import java.time.LocalDateTime;

public interface Stub {
    static String getProfileImage(){
        return "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDJfMjM5%2FMDAxNTc1MjQ5NzIyOTMw.qW1xbu96TGcuxCSh_WH9fvqjiQd8Sy8GvtWe7s_WjTcg._QJQWDySEPuRmJC4LGXq_587vzAnmyWztycwWlAdaXwg.JPEG.ty5568yr%2FKakaoTalk_20191202_101754417.jpg&type=ff332_332";
    }

    static LocalDateTime getLocalDateTime(){
        return LocalDateTime.of(2022,8,29,6,49,12);
    }
}
