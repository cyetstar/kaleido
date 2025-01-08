package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.ISystem;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.dto.BaseDTO;
import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.core.utils.ClassHelper;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * @Author cyetstar
 * @Date 2022-05-31 00:35:00
 * @Description TODO
 */
public abstract class KaleidoBaseServiceImpl<ID extends Serializable, M extends BaseMapper<T>, T extends IdEntity<ID>, D extends BaseDTO<ID>>
        extends AbstractBaseServiceImpl<M, T, D> {

    protected String bizTable = getBizTable();

    public String getBizTable() {
        String shortClassName = ClassUtils.getShortClassName(getEntityClass());
        shortClassName = StringUtils.removeEnd(shortClassName, ClassHelper.CLASS_SUFFIX_DO);
        return StringUtils.uncapitalize(shortClassName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageResult<D> page(@Nullable D dto, Page page) {
        LambdaQueryWrapper<T> queryWrapper = null;
        if (dto != null) {
            queryWrapper = (LambdaQueryWrapper<T>) genQueryWrapper(dto);
        }
        Page<T> selectPage = getBaseMapper().selectPage(page, queryWrapper);
        return convertToDTO(selectPage);
    }

    @Override
    public List<D> list(@Nullable D dto) {
        LambdaQueryWrapper<T> queryWrapper = null;
        if (dto != null) {
            queryWrapper = (LambdaQueryWrapper<T>) genQueryWrapper(dto);
        }
        List<T> list = getBaseMapper().selectList(queryWrapper);
        return convertToDTO(list);
    }

    @Override
    @Transactional
    public D insert(D dto) {
        beforeInsert(dto);
        dto = super.insert(dto);
        return dto;
    }

    @Override
    @Transactional
    public boolean update(D dto) {
        beforeUpdate(dto);
        boolean result = super.update(dto);
        return result;
    }

    protected void beforeInsert(D dto) {
        if (!checkUnique(dto)) {
            throw new ServiceException(20001, "存在重复(" + getBizTable() + ")");
        }
        if (dto instanceof ISystem) {
            @SuppressWarnings("unchecked")
            ISystem<ID> systemDTO = (ISystem<ID>) dto;
            systemDTO.setCjsj(DateTimeUtils.now());
            systemDTO.setXgsj(DateTimeUtils.now());
        }

    }

    protected void beforeUpdate(D dto) {
        if (!checkUnique(dto)) {
            throw new ServiceException(20001, "存在重复(" + getBizTable() + ")");
        }
        if (dto instanceof ISystem) {
            @SuppressWarnings("unchecked")
            ISystem<ID> systemDTO = (ISystem<ID>) dto;
            systemDTO.setXgsj(DateTimeUtils.now());
        }
    }

    protected boolean checkUnique(D dto) {
        return true;
    }

    protected boolean doCheckUnique(D newDTO, D existDTO) {
        if (existDTO == null) {
            return true;
        }
        if (newDTO.getId() != null) {
            return newDTO.getId().equals(existDTO.getId());
        } else {
            return false;
        }
    }

}
