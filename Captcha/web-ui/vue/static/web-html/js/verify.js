/*! Verify&admin MIT License by anji-plus*/

;(function($, window, document,undefined) {

    //请求图片get事件
    function getPictrue(data,baseUrl){
		return new Promise((resolve,reject)=>{
			$.ajax({
				type : "post",
				contentType: "application/json;charset=UTF-8",
				url : baseUrl + "/captcha/get",
				data :JSON.stringify(data),
				success:function(res){
					resolve(res)
				},
				fail: function(err) {
					reject(err)
				}
			})
		})

	}
	//验证图片check事件
	function checkPictrue(data,baseUrl){
		return new Promise((resolve,reject)=>{
			$.ajax({
				type : "post",
				contentType: "application/json;charset=UTF-8",
				url : baseUrl + "/captcha/check",
				data :JSON.stringify(data),
				success:function(res){
					resolve(res)
				},
				fail: function(err) {
					reject(err)
				}
			})
		})
	}

    //定义Slide的构造函数
    var Slide = function(ele, opt) {
		this.$element = ele,
		this.backToken = null,
		this.moveLeftDistance = 0,
        this.defaults = {
			baseUrl:"https://captcha.anji-plus.com/captcha-api",
			containerId:'',
        	captchaType:"blockPuzzle",
        	mode : 'fixed',	//弹出式pop，固定fixed
        	vOffset: 5,
            vSpace : 5,
            explain : '向右滑动完成验证',
            imgSize : {
	        	width: '310px',
	        	height: '155px',
			},
			blockSize : {
	        	width: '50px',
	        	height: '50px',
	        },
            circleRadius: '10px',
	        barSize : {
	        	width : '310px',
	        	height : '50px',
	        },
            ready : function(){},
        	success : function(){},
            error : function(){}

        },
        this.options = $.extend({}, this.defaults, opt)
    };


    //定义Slide的方法
    Slide.prototype = {
        init: function() {
			var _this = this;
        	//加载页面
        	this.loadDom();
			_this.refresh();
        	this.options.ready();

        	this.$element[0].onselectstart = document.body.ondrag = function(){
				return false;
			};

        	if(this.options.mode == 'pop')	{

				_this.$element.find('.verifybox-close').on('click', function() {
					_this.$element.find(".mask").css("display","none");
					_this.refresh();
				});

				let clickBtn = document.getElementById(this.options.containerId);
				clickBtn && (clickBtn.onclick = function(){
					_this.$element.find(".mask").css("display","block");
				})
        	}

        	//按下
        	this.htmlDoms.move_block.on('touchstart', function(e) {
        		_this.start(e);
        	});

        	this.htmlDoms.move_block.on('mousedown', function(e) {
        		_this.start(e);
        	});

        	//拖动
            window.addEventListener("touchmove", function(e) {
            	_this.move(e);
            });

            window.addEventListener("mousemove", function(e) {
            	_this.move(e);
            });

            //鼠标松开
            window.addEventListener("touchend", function() {
            	_this.end();
            });
            window.addEventListener("mouseup", function() {
            	_this.end();
            });

            //刷新
            _this.$element.find('.verify-refresh').on('click', function() {
				_this.refresh();
            });
        },

        //初始化加载
        loadDom : function() {
			this.status = false;	//鼠标状态
        	this.isEnd = false;		//是够验证完成
			this.setSize = this.resetSize(this);	//重新设置宽度高度
			this.plusWidth = 0;
			this.plusHeight = 0;
            this.x = 0;
            this.y = 0;
        	var panelHtml = '';
        	var wrapHtml = '';
			this.lengthPercent = (parseInt(this.setSize.img_width)-parseInt(this.setSize.block_width)- parseInt(this.setSize.circle_radius) - parseInt(this.setSize.circle_radius) * 0.8)/(parseInt(this.setSize.img_width)-parseInt(this.setSize.bar_height));

			wrapStartHtml = `<div class="mask">
								<div class="verifybox" style="width:${parseInt(this.setSize.img_width)+30}px">
									<div class="verifybox-top">
										请完成安全验证
										<span class="verifybox-close">
											<i class="iconfont icon-close"></i>
										</span>
									</div>
									<div class="verifybox-bottom" style="padding:15px">
										<div style="position: relative;">`

			if (this.options.mode == 'pop') {
				panelHtml = wrapStartHtml
			}
			panelHtml += `<div class="verify-img-out">
							<div class="verify-img-panel">
								<div class="verify-refresh" style="z-index:3">
									<i class="iconfont icon-refresh"></i>
								</div>
								<span class="verify-tips"  class="suc-bg"></span>
								<img src="" class="backImg" style="width:100%;height:100%;display:block">
							</div>
						</div>`;

			this.plusWidth = parseInt(this.setSize.block_width) + parseInt(this.setSize.circle_radius) * 2 - parseInt(this.setSize.circle_radius) * 0.2;
			this.plusHeight = parseInt(this.setSize.block_height) + parseInt(this.setSize.circle_radius) * 2 - parseInt(this.setSize.circle_radius) * 0.2;

			panelHtml +=`<div class="verify-bar-area" style="{width:${this.setSize.img_width},height:${this.setSize.bar_height},'line-height':${this.setSize.bar_height}">
									<span  class="verify-msg">${this.options.explain}</span>
									<div class="verify-left-bar">
										<span class="verify-msg"></span>
										<div  class="verify-move-block">
											<i  class="verify-icon iconfont icon-right"></i>
											<div class="verify-sub-block">
												<img src="" class="bock-backImg" alt=""  style="width:100%;height:100%;display:block">
											</div>
										</div>
									</div>
								</div>`;
			wrapEndHtml = `</div></div></div></div>`
			if (this.options.mode == 'pop') {
				panelHtml += wrapEndHtml
			}

        	this.$element.append(panelHtml);
        	this.htmlDoms = {
        		tips: this.$element.find('.verify-tips'),
        		sub_block : this.$element.find('.verify-sub-block'),
        		out_panel : this.$element.find('.verify-img-out'),
        		img_panel : this.$element.find('.verify-img-panel'),
				img_canvas : this.$element.find('.verify-img-canvas'),
        		bar_area : this.$element.find('.verify-bar-area'),
        		move_block : this.$element.find('.verify-move-block'),
        		left_bar : this.$element.find('.verify-left-bar'),
        		msg : this.$element.find('.verify-msg'),
        		icon : this.$element.find('.verify-icon'),
        		refresh :this.$element.find('.verify-refresh')
        	};

        	this.$element.css('position', 'relative');

			this.htmlDoms.sub_block.css({'height':this.setSize.img_height,'width':Math.ceil(parseInt(this.setSize.img_width)*50/310)+ 'px',
									'top':-(parseInt(this.setSize.img_height) + this.options.vSpace) + 'px'})
			this.htmlDoms.out_panel.css('height', parseInt(this.setSize.img_height) + this.options.vSpace + 'px');
			this.htmlDoms.img_panel.css({'width': this.setSize.img_width, 'height': this.setSize.img_height});
			this.htmlDoms.bar_area.css({'width': this.setSize.img_width, 'height': this.setSize.bar_height, 'line-height':this.setSize.bar_height});
        	this.htmlDoms.move_block.css({'width': this.setSize.bar_height, 'height': this.setSize.bar_height});
        	this.htmlDoms.left_bar.css({'width': this.setSize.bar_height, 'height': this.setSize.bar_height});
        },


        //鼠标按下
        start: function(e) {
			this.startMoveTime = new Date().getTime();
        	if(this.isEnd == false) {
	        	this.htmlDoms.msg.text('');
	        	this.htmlDoms.move_block.css('background-color', '#337ab7');
	        	this.htmlDoms.left_bar.css('border-color', '#337AB7');
	        	this.htmlDoms.icon.css('color', '#fff');
	        	e.stopPropagation();
	        	this.status = true;
        	}
        },

        //鼠标移动
        move: function(e) {
        	if(this.status && this.isEnd == false) {
	            if(!e.touches) {    //兼容移动端
	                var x = e.clientX;
	            }else {     //兼容PC端
	                var x = e.touches[0].pageX;
	            }
				var bar_area_left = this.htmlDoms.bar_area[0].getBoundingClientRect().left;
                var move_block_left = x - bar_area_left; //小方块相对于父元素的left值
				if(move_block_left >= (this.htmlDoms.bar_area[0].offsetWidth - parseInt(this.setSize.bar_height) + parseInt(parseInt(this.setSize.block_width)/2) - 2) ) {
					move_block_left = (this.htmlDoms.bar_area[0].offsetWidth - parseInt(this.setSize.bar_height) + parseInt(parseInt(this.setSize.block_width)/2)- 2);
				}
	            if(move_block_left <= parseInt(parseInt(this.setSize.block_width)/2)) {
            		move_block_left = parseInt(parseInt(this.setSize.block_width)/2);
            	}
				//拖动后小方块的left值

	            this.htmlDoms.move_block.css('left', move_block_left-parseInt(parseInt(this.setSize.block_width)/2) + "px");
	            this.htmlDoms.left_bar.css('width', move_block_left-parseInt(parseInt(this.setSize.block_width)/2) + "px");
				this.htmlDoms.sub_block.css('left', "0px");

				this.moveLeftDistance = move_block_left-parseInt(parseInt(this.setSize.block_width)/2)
	        }
        },

        //鼠标松开
        end: function() {
			this.endMovetime = new Date().getTime();
        	var _this = this;
        	//判断是否重合
        	if(this.status  && this.isEnd == false) {
				var vOffset = parseInt(this.options.vOffset);
				this.moveLeftDistance = this.moveLeftDistance * 310/ parseInt(this.setSize.img_width)
				//图片滑动
				let data = {
					captchaType:this.options.captchaType,
					"pointJson":aesEncrypt(JSON.stringify({x:this.moveLeftDistance,y:5.0})),
					"token":this.backToken
				}
				var captchaVerification = aesEncrypt(this.backToken+'---'+JSON.stringify({x:this.moveLeftDistance,y:5.0}))
				checkPictrue(data,this.options.baseUrl).then(res=>{
					// 请求反正成功的判断
					if (res.repCode=="0000") {
						this.htmlDoms.move_block.css('background-color', '#5cb85c');
						this.htmlDoms.left_bar.css({'border-color': '#5cb85c', 'background-color': '#fff'});
						this.htmlDoms.icon.css('color', '#fff');
						this.htmlDoms.icon.removeClass('icon-right');
						this.htmlDoms.icon.addClass('icon-check');

						//提示框
						this.htmlDoms.tips.addClass('suc-bg').removeClass('err-bg')
						this.htmlDoms.tips.css({"display":"block",animation:"move 1s cubic-bezier(0, 0, 0.39, 1.01)"});
						this.htmlDoms.tips.text(`${((this.endMovetime-this.startMoveTime)/1000).toFixed(2)}s验证成功`)

						this.isEnd = true;
						setTimeout(res=>{
							_this.$element.find(".mask").css("display","none");
							this.htmlDoms.tips.css({"display":"none",animation:"none"});
							_this.refresh();
						},1000)
						this.options.success({captchaVerification});
					}else{
						this.htmlDoms.move_block.css('background-color', '#d9534f');
						this.htmlDoms.left_bar.css('border-color', '#d9534f');
						this.htmlDoms.icon.css('color', '#fff');
						this.htmlDoms.icon.removeClass('icon-right');
						this.htmlDoms.icon.addClass('icon-close');

						this.htmlDoms.tips.addClass('err-bg').removeClass('suc-bg')
						this.htmlDoms.tips.css({"display":"block",animation:"move 1s cubic-bezier(0, 0, 0.39, 1.01)"});
						this.htmlDoms.tips.text(`验证失败`)

						setTimeout(function () {
							_this.refresh();
						}, 400);

						setTimeout(()=>{
							this.htmlDoms.tips.css({"display":"none",animation:"none"});
						},1000)
						this.options.error(this);
					}
				})
	            this.status = false;
        	}
		},

        resetSize : function(obj) {
        	var img_width,img_height,bar_width,bar_height,block_width,block_height,circle_radius;	//图片的宽度、高度，移动条的宽度、高度
        	var parentWidth = obj.$element.parent().width() || $(window).width();
        	var parentHeight = obj.$element.parent().height() || $(window).height();

       		if(obj.options.imgSize.width.indexOf('%')!= -1){
        		img_width = parseInt(obj.options.imgSize.width)/100 * parentWidth + 'px';
		　　}else {
				img_width = obj.options.imgSize.width;
			}

			if(obj.options.imgSize.height.indexOf('%')!= -1){
        		img_height = parseInt(obj.options.imgSize.height)/100 * parentHeight + 'px';
		　　}else {
				img_height = obj.options.imgSize.height;
			}

			if(obj.options.barSize.width.indexOf('%')!= -1){
        		bar_width = parseInt(obj.options.barSize.width)/100 * parentWidth + 'px';
		　　}else {
				bar_width = obj.options.barSize.width;
			}

			if(obj.options.barSize.height.indexOf('%')!= -1){
        		bar_height = parseInt(obj.options.barSize.height)/100 * parentHeight + 'px';
		　　}else {
				bar_height = obj.options.barSize.height;
			}

			if(obj.options.blockSize) {
				if(obj.options.blockSize.width.indexOf('%')!= -1){
					block_width = parseInt(obj.options.blockSize.width)/100 * parentWidth + 'px';
			　　}else {
					block_width = obj.options.blockSize.width;
				}


				if(obj.options.blockSize.height.indexOf('%')!= -1){
					block_height = parseInt(obj.options.blockSize.height)/100 * parentHeight + 'px';
			　　}else {
					block_height = obj.options.blockSize.height;
				}
			}

			if(obj.options.circleRadius) {
				if(obj.options.circleRadius.indexOf('%')!= -1){
					circle_radius = parseInt(obj.options.circleRadius)/100 * parentHeight + 'px';
			　　}else {
					circle_radius = obj.options.circleRadius;
				}
			}

			return {img_width : img_width, img_height : img_height, bar_width : bar_width, bar_height : bar_height, block_width : block_width, block_height : block_height, circle_radius : circle_radius};
       	},

        //刷新
        refresh: function() {
			var _this = this;
        	this.htmlDoms.refresh.show();
        	this.$element.find('.verify-msg:eq(1)').text('');
        	this.$element.find('.verify-msg:eq(1)').css('color', '#000');
        	this.htmlDoms.move_block.animate({'left':'0px'}, 'fast');
			this.htmlDoms.left_bar.animate({'width': parseInt(this.setSize.bar_height)}, 'fast');
			this.htmlDoms.left_bar.css({'border-color': '#ddd'});

			this.htmlDoms.move_block.css('background-color', '#fff');
			this.htmlDoms.icon.css('color', '#000');
			this.htmlDoms.icon.removeClass('icon-close');
			this.htmlDoms.icon.addClass('icon-right');
			this.$element.find('.verify-msg:eq(0)').text(this.options.explain);
			this.isEnd = false;
			getPictrue({captchaType:"blockPuzzle"},this.options.baseUrl).then(res=>{
				if (res.repCode=="0000") {
					this.$element.find(".backImg")[0].src = 'data:image/png;base64,'+res.repData.originalImageBase64
					this.$element.find(".bock-backImg")[0].src = 'data:image/png;base64,'+res.repData.jigsawImageBase64
					this.backToken = res.repData.token
				}
			});
			this.htmlDoms.sub_block.css('left', "0px");
        },
    };


    //定义Points的构造函数
    var Points = function(ele, opt) {
		this.$element = ele,
		this.backToken = null,
        this.defaults = {
			baseUrl:"https://captcha.anji-plus.com/captcha-api",
			captchaType:"clickWord",
			containerId:'',
        	mode : 'fixed',	//弹出式pop，固定fixed
		    checkNum : 3,	//校对的文字数量
		    vSpace : 5,	//间隔
        	imgSize : {
	        	width: '310px',
	        	height: '155px',
	        },
	        barSize : {
	        	width : '310px',
	        	height : '50px',
	        },
	        ready : function(){},
        	success : function(){},
            error : function(){}
        },
        this.options = $.extend({}, this.defaults, opt)
    };

    //定义Points的方法
    Points.prototype = {

    	init : function() {

			var _this = this;
			//加载页面
        	_this.loadDom();

        	_this.refresh();
        	_this.options.ready();

        	this.$element[0].onselectstart = document.body.ondrag = function(){
				return false;
			};

			if(this.options.mode == 'pop')	{

				_this.$element.find('.verifybox-close').on('click', function() {
					_this.$element.find(".mask").css("display","none");
				});

				let clickBtn = document.getElementById(this.options.containerId);
				clickBtn && (clickBtn.onclick = function(){
					_this.$element.find(".mask").css("display","block");
				})

        	}

		 	// 注册点击验证事件
        	_this.$element.find('.back-img').on('click', function(e) {

				_this.checkPosArr.push(_this.getMousePos(this, e));

				if(_this.num == _this.options.checkNum) {
					_this.num = _this.createPoint(_this.getMousePos(this, e));
					 //按比例转换坐标值
					 _this.checkPosArr = _this.pointTransfrom(_this.checkPosArr,_this.setSize);
					setTimeout(()=>{
						let data = {
							captchaType:_this.options.captchaType,
							"pointJson":aesEncrypt(JSON.stringify(_this.checkPosArr)),
							"token":_this.backToken
						}

						var captchaVerification = aesEncrypt(_this.backToken+'---'+JSON.stringify(_this.checkPosArr))
						checkPictrue(data,this.options.baseUrl).then(res=>{
							if (res.repCode=="0000") {
								_this.$element.find('.verify-bar-area').css({'color': '#4cae4c', 'border-color': '#5cb85c'});
								_this.$element.find('.verify-msg').text('验证成功');
								_this.$element.find('.verify-refresh').hide();
								_this.$element.find('.verify-img-panel').unbind('click');
								setTimeout(res=>{
									_this.$element.find(".mask").css("display","none");
									_this.refresh();
								},1000)
								_this.options.success({captchaVerification});
							}else{
								_this.options.error(_this);
								_this.$element.find('.verify-bar-area').css({'color': '#d9534f', 'border-color': '#d9534f'});
								_this.$element.find('.verify-msg').text('验证失败');
								setTimeout(function () {
									_this.$element.find('.verify-bar-area').css({'color': '#000','border-color': '#ddd'});
									_this.refresh();
								}, 400);
							}
						})
					}, 400);

				}
				if(_this.num < _this.options.checkNum) {
					_this.num = _this.createPoint(_this.getMousePos(this, e));
				}
        	});

        	 //刷新
            _this.$element.find('.verify-refresh').on('click', function() {
            	_this.refresh();
            });

    	},

    	//加载页面
    	loadDom : function() {

    		this.fontPos = [];	//选中的坐标信息
    		this.checkPosArr = [];	//用户点击的坐标
    		this.num = 1;	//点击的记数

			var panelHtml = '';
			var wrapStartHtml = '';

			this.setSize = Slide.prototype.resetSize(this);	//重新设置宽度高度

			wrapStartHtml = `<div class="mask">
								<div class="verifybox" style="width:${parseInt(this.setSize.img_width)+30}px">
									<div class="verifybox-top">
										请完成安全验证
										<span class="verifybox-close">
											<i class="iconfont icon-close"></i>
										</span>
									</div>
									<div class="verifybox-bottom" style="padding:15px">
										<div style="position: relative;">`

			if (this.options.mode == 'pop') {
				panelHtml = wrapStartHtml
			}

			panelHtml += `<div class="verify-img-out">
							<div class="verify-img-panel">
								<div class="verify-refresh" style="z-index:3">
									<i class="iconfont icon-refresh"></i>
								</div>
								<img src='' class="back-img" width="${this.setSize.img_width}" height="${this.setSize.img_height}">
							</div>
						</div>
						<div class="verify-bar-area" style="{width:${this.setSize.img_width},height:${this.setSize.bar_height},'line-height':${this.setSize.bar_height}">
							<span  class="verify-msg"></span>
						</div>`;

			wrapEndHtml = `</div></div></div></div>`

			if (this.options.mode == 'pop') {
				panelHtml += wrapEndHtml
			}

        	this.$element.append(panelHtml);

        	this.htmlDoms = {
				back_img : this.$element.find('.back-img'),
        		out_panel : this.$element.find('.verify-img-out'),
        		img_panel : this.$element.find('.verify-img-panel'),
        		bar_area : this.$element.find('.verify-bar-area'),
        		msg : this.$element.find('.verify-msg'),
        	};

        	this.$element.css('position', 'relative');

        	this.htmlDoms.out_panel.css('height', parseInt(this.setSize.img_height) + this.options.vSpace + 'px');
    		this.htmlDoms.img_panel.css({'width': this.setSize.img_width, 'height': this.setSize.img_height, 'background-size' : this.setSize.img_width + ' '+ this.setSize.img_height, 'margin-bottom': this.options.vSpace + 'px'});
    		this.htmlDoms.bar_area.css({'width': this.setSize.img_width, 'height': this.setSize.bar_height, 'line-height':this.setSize.bar_height});

    	},

    	//获取坐标
    	getMousePos :function(obj, event) {
            var e = event || window.event;
            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
            var x = e.clientX - ($(obj).offset().left - $(window).scrollLeft());
    		var y = e.clientY - ($(obj).offset().top - $(window).scrollTop());

            return {'x': x, 'y': y};
     	},

       	//创建坐标点
       	createPoint : function (pos) {
			   this.htmlDoms.img_panel.append(`<div class="point-area" style="background-color:#1abd6c;color:#fff;z-index:9999;width:20px;height:20px;text-align:center;line-height:20px;border-radius: 50%;position:absolute;
			   										top:${parseInt(pos.y-10)}px;left:${parseInt(pos.x-10)}px;">${this.num}</div>`);
       		return ++this.num;
       	},

       	//刷新
        refresh: function() {
        	var _this = this;
        	this.$element.find('.point-area').remove();
        	this.fontPos = [];
        	this.checkPosArr = [];
        	this.num = 1;
			getPictrue({captchaType:"clickWord"},this.options.baseUrl).then(res=>{
				if (res.repCode=="0000") {
					this.htmlDoms.back_img[0].src ='data:image/png;base64,'+ res.repData.originalImageBase64
					this.backToken = res.repData.token
					let text = '请依次点击【' + res.repData.wordList.join(",") + '】'
					_this.$element.find('.verify-msg').text(text);
				}
			})

		},
		pointTransfrom:function(pointArr,imgSize){
			var newPointArr = pointArr.map(p=>{
				let x = Math.round(310 * p.x/parseInt(imgSize.img_width))
				let y =Math.round(155 * p.y/parseInt(imgSize.img_height))
				return {x,y}
			})
			return newPointArr
		}
    };
    //在插件中使用slideVerify对象
    $.fn.slideVerify = function(options, callbacks) {
        var slide = new Slide(this, options);
        slide.init();
    };

    //在插件中使用clickVerify对象
    $.fn.pointsVerify = function(options, callbacks) {
        var points = new Points(this, options);
        points.init();
    };

})(jQuery, window, document);
