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
import java.util.List;

/**
 * @Author cyetstar
 * @Date 2022-05-31 00:35:00
 * @Description TODO
 */
public abstract class KaleidoBaseServiceImpl<M extends BaseMapper<T>, T extends IdEntity, D extends BaseDTO> extends AbstractBaseServiceImpl<M, T, D> {

    protected String bizTable = getBizTable();

    public String getBizTable() {
        String shortClassName = ClassUtils.getShortClassName(getEntityClass());
        shortClassName = StringUtils.removeEnd(shortClassName, ClassHelper.CLASS_SUFFIX_DO);
        return StringUtils.uncapitalize(shortClassName);
    }

    @Override
    public PageResult<D> page(@Nullable D dto, Page page) {
        LambdaQueryWrapper<T> queryWrapper = null;
        if (dto != null) {
            queryWrapper = (LambdaQueryWrapper) genQueryWrapper(dto);
        }
        page = getBaseMapper().selectPage(page, queryWrapper);
        return convertToDTO(page);
    }

    @Override
    public List<D> list(@Nullable D dto) {
        LambdaQueryWrapper<T> queryWrapper = null;
        if (dto != null) {
            queryWrapper = (LambdaQueryWrapper) genQueryWrapper(dto);
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
            ISystem systemDTO = (ISystem) dto;
            systemDTO.setCjsj(DateTimeUtils.now());
            systemDTO.setXgsj(DateTimeUtils.now());
        }

    }

    protected void beforeUpdate(D dto) {
        if (!checkUnique(dto)) {
            throw new ServiceException(20001, "存在重复(" + getBizTable() + ")");
        }
        if (dto instanceof ISystem) {
            ISystem systemDTO = (ISystem) dto;
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
