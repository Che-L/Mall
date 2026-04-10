package com.project.mall.search;

import com.project.mall.common.api.ResultCode;
import com.project.mall.search.controller.EsProductController;
import com.project.mall.search.service.EsProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EsProductController.class)
class EsProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EsProductService esProductService;

    @Test
    void importAllReturnsJson() throws Exception {
        when(esProductService.importAll()).thenReturn(3);
        mockMvc.perform(post("/esProduct/importAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").value(3));
    }

    @Test
    void searchSimpleDelegatesToService() throws Exception {
        when(esProductService.search(isNull(), anyInt(), anyInt())).thenReturn(
                new org.springframework.data.domain.PageImpl<>(java.util.List.of()));
        mockMvc.perform(get("/esProduct/search/simple")
                        .param("pageNum", "0")
                        .param("pageSize", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
    }

    @Test
    void deleteByIdReturnsOk() throws Exception {
        mockMvc.perform(get("/esProduct/delete/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
    }

    @Test
    void searchWithFiltersReturnsOk() throws Exception {
        when(esProductService.search(any(), any(), any(), anyInt(), anyInt(), anyInt())).thenReturn(
                new org.springframework.data.domain.PageImpl<>(java.util.List.of()));
        mockMvc.perform(get("/esProduct/search")
                        .param("keyword", "phone")
                        .param("brandId", "1")
                        .param("productCategoryId", "2")
                        .param("pageNum", "0")
                        .param("pageSize", "10")
                        .param("sort", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
    }

    @Test
    void deleteBatchReturnsOk() throws Exception {
        mockMvc.perform(post("/esProduct/delete/batch")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("ids", "1", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCode.SUCCESS.getCode()));
    }
}
