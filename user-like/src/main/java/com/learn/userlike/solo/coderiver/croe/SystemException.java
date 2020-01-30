package com.learn.userlike.solo.coderiver.croe;

import com.learn.userlike.solo.coderiver.exception.UserException;
import com.learn.userlike.solo.coderiver.vo.ResultVO;

/**
 * 系统级异常
 *
 * @Author: li cong zhi
 * @CreateDate: 2019/12/28 23:45
 * @UpdateUser: li cong zhi
 * @UpdateDate: 2019/12/28 23:45
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SystemException extends UserException {

    private ResultVO resultVO = new ResultVO();

    public SystemException(ResultVO resultVO) {
        super(resultVO.getCode() + ":" + resultVO.getMsg());
        this.resultVO = resultVO;
    }

    public SystemException(Integer code, String massage) {
        super(code + ":" + massage);
        this.resultVO.setCode(code);
        this.resultVO.setMsg(massage);
    }

    public SystemException(ResultVO resultVO, Throwable cause) {
        super(resultVO.getCode() + ":" + resultVO.getMsg(), cause);
        this.resultVO = resultVO;
    }

    public SystemException(Integer code, String massage, Throwable cause) {
        super(code + ":" + massage, cause);
        this.resultVO.setCode(code);
        this.resultVO.setMsg(massage);
    }

}
