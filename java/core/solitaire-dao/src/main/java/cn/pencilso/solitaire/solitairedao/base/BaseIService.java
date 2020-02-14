package cn.pencilso.solitaire.solitairedao.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author pencilso
 * @date 2020/1/24 11:29 上午
 */
@Validated
public interface BaseIService<M extends BaseModel> extends IService<M> {


    /**
     * 根据业务唯一ID查询
     *
     * @param mid 业务唯一ID
     * @return
     */
    default Optional<M> getByMid(Long mid) {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", mid);
        Collection<M> listByMap = listByMap(map);
        Iterator<M> iterator = listByMap.iterator();
        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        }
        return Optional.empty();
    }


    default List<M> getByMids(@NotNull Collection<Long> mids) {
        if (mids.isEmpty()) return new ArrayList<>();
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("mid", mids);
        return list(queryWrapper);
    }

    /**
     * 根据业务ID删除
     *
     * @param mid
     * @return
     */
    default boolean removeByMid(@NotNull Long mid) {
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mid", mid);
        return remove(queryWrapper);
    }


    /**
     * 根据业务唯一ID 更新Model
     *
     * @param model
     * @param mid
     * @return
     */
    default boolean updateModelByMid(@NotNull M model, Long mid) {
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mid", mid);
        return update(model, queryWrapper);
    }

    /**
     * remove batch by business mid
     *
     * @param mids
     * @return
     */
    default boolean removeByMids(@NotNull Collection<Long> mids) {
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("mid", mids);
        return remove(queryWrapper);
    }

    /**
     * 构建资源路径
     *
     * @param model
     * @return
     */
    default M buildResourcePath(M model) {
        return model;
    }

    /**
     * 构建资源路径
     *
     * @param modelList
     * @return
     */
    default List<M> buildResourcePath(List<M> modelList) {
        return modelList;
    }
}
