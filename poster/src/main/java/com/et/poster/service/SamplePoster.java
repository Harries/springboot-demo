package com.et.poster.service;

import com.quaint.poster.annotation.PosterBackground;
import com.quaint.poster.annotation.PosterFontCss;
import com.quaint.poster.annotation.PosterImageCss;
import com.quaint.poster.core.abst.AbstractDefaultPoster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author quaint
 * @date 30 March 2020
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SamplePoster extends AbstractDefaultPoster {

    /**
     * 背景图
     */
    @PosterBackground(width = 640,height = 990)
    private BufferedImage backgroundImage;
	/**
	 * 主图
	 */
	@PosterImageCss(position = {0,0},width = 640,height = 400)
	private BufferedImage mainImage;
	/**
	 * 网站站标
	 */
	@PosterImageCss(position = {0,800},width = 640, height = 10)
	private BufferedImage xuxian;
	/**
	 * 网站站标
	 */
	@PosterImageCss(position = {10,820},width = 470, height = 130)
	private BufferedImage siteSlogon;
    /**
     * 文章链接二维码
     */
    @PosterImageCss(position = {520,830},width = 100, height = 100)
    private BufferedImage postQrcode;

    /**
     * 文章标题
     */
    @PosterFontCss(position = {20,440}, color = {0,0,0},size = 30,canNewLine={1,600,7})
    private String postTitle;
	/**
	 * 文章日期
	 */
	@PosterFontCss(position = {20,540}, color = {33,33,33},size = 20)
	private String postDate;
    /**
     * 文章简介
     */
    @PosterFontCss(position = {20,570}, size = 20, color = {0,0,0},canNewLine={1,600,7})
    private String posttitleDesc;



    @Tolerate
    public SamplePoster() {}
}
