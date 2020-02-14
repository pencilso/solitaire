package cn.pencilso.solitaire.common.model.respone;

import cn.pencilso.solitaire.common.constant.ResponeWrapperConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/1/24 4:17 下午
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResponeWrapper<V> {

    private int code;
    private V data;
    private String message;

    public static final ResponeWrapper<String> RESPONE_SUCCESS = new ResponeWrapper<>(0, null, "操作成功");
    public static final ResponeWrapper<String> RESPONE_FAIL = new ResponeWrapper<>(ResponeWrapperConst.ERROR_OPERATE_FAIL, null, "操作失败");


    /**
     * data page respone wrapper
     *
     * @param <L> list
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    public static class ResponePageWrapper<L> extends ResponeWrapper<L> {
        /**
         * data total count
         */
        private int totalData;
        /**
         * data page total count
         */
        private int totalPages;
        /**
         * current page
         */
        private int currentPage;
    }
}
