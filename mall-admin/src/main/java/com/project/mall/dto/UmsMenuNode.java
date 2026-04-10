package com.project.mall.dto;

import com.project.mall.model.UmsMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 鍚庡彴鑿滃崟鑺傜偣灏佽
 * Created by macro on 2020/2/4.
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    private List<UmsMenuNode> children;
}
