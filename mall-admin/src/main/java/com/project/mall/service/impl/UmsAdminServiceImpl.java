package com.project.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.project.mall.bo.AdminUserDetails;
import com.project.mall.common.exception.Asserts;
import com.project.mall.common.util.RequestUtil;
import com.project.mall.dao.UmsAdminRoleRelationDao;
import com.project.mall.dto.UmsAdminParam;
import com.project.mall.dto.UpdateAdminPasswordParam;
import com.project.mall.mapper.UmsAdminLoginLogMapper;
import com.project.mall.mapper.UmsAdminMapper;
import com.project.mall.mapper.UmsAdminRoleRelationMapper;
import com.project.mall.model.*;
import com.project.mall.security.util.JwtTokenUtil;
import com.project.mall.security.util.SpringUtil;
import com.project.mall.service.UmsAdminCacheService;
import com.project.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 閸氬骸褰撮悽銊﹀煕缁狅紕鎮奡ervice鐎圭偟骞囩猾?
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //閸忓牅绮犵紓鎾崇摠娑擃叀骞忛崣鏍ㄦ殶閹?
        UmsAdmin admin = getCacheService().getAdmin(username);
        if (admin != null) return admin;
        //缂傛挸鐡ㄦ稉顓熺梾閺堝绮犻弫鐗堝祦鎼存挷鑵戦懢宄板絿
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            //鐏忓棙鏆熼幑顔肩氨娑擃厾娈戦弫鐗堝祦鐎涙ê鍙嗙紓鎾崇摠娑?
            getCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //閺屻儴顕楅弰顖氭儊閺堝娴夐崥宀€鏁ら幋宄版倳閻ㄥ嫮鏁ら幋?
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //鐏忓棗鐦戦惍浣界箻鐞涘苯濮炵€靛棙鎼锋担?
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //鐎靛棛鐖滈棁鈧憰浣割吂閹撮顏崝鐘茬槕閸氬簼绱堕柅?
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("Incorrect username or password");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("Account is disabled");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("閻ц缍嶅鍌氱埗:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 濞ｈ濮為惂璇茬秿鐠佹澘缍?
     * @param username 閻劍鍩涢崥?
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if(admin==null) return;
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLogMapper.insert(loginLog);
    }

    /**
     * 閺嶈宓侀悽銊﹀煕閸氬秳鎱ㄩ弨鍦瑜版洘妞傞梻?
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin record = new UmsAdmin();
        record.setLoginTime(new Date());
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(record, example);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
        }
        return adminMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            //娑撳骸甯崝鐘茬槕鐎靛棛鐖滈惄绋挎倱閻ㄥ嫪绗夐棁鈧憰浣锋叏閺€?
            admin.setPassword(null);
        }else{
            //娑撳骸甯崝鐘茬槕鐎靛棛鐖滄稉宥呮倱閻ㄥ嫰娓剁憰浣稿鐎靛棔鎱ㄩ弨?
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        int count = adminMapper.updateByPrimaryKeySelective(admin);
        getCacheService().delAdmin(id);
        return count;
    }

    @Override
    public int delete(Long id) {
        getCacheService().delAdmin(id);
        int count = adminMapper.deleteByPrimaryKey(id);
        getCacheService().delResourceList(id);
        return count;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //閸忓牆鍨归梽銈呭斧閺夈儳娈戦崗宕囬兇
        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //瀵よ櫣鐝涢弬鏉垮彠缁?
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationDao.insertList(list);
        }
        getCacheService().delResourceList(adminId);
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //閸忓牅绮犵紓鎾崇摠娑擃叀骞忛崣鏍ㄦ殶閹?
        List<UmsResource> resourceList = getCacheService().getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        //缂傛挸鐡ㄦ稉顓熺梾閺堝绮犻弫鐗堝祦鎼存挷鑵戦懢宄板絿
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            //鐏忓棙鏆熼幑顔肩氨娑擃厾娈戦弫鐗堝祦鐎涙ê鍙嗙紓鎾崇摠娑?
            getCacheService().setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminMapper.updateByPrimaryKey(umsAdmin);
        getCacheService().delAdmin(umsAdmin.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //閼惧嘲褰囬悽銊﹀煕娣団剝浼?
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("閻劍鍩涢崥宥嗗灗鐎靛棛鐖滈柨娆掝嚖");
    }

    @Override
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }
}
