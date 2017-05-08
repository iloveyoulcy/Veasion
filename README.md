我的[个人网站](http://59.110.241.52) ，架构：自己写的轻量级MVC框架 Veasion. <br/>
包括模型层model用的字段Json格式，控制层control 采用反射机制，自定义类无需继承. <br/>
> 首先这是一个control，类的命名方式xxx+Veasion <br/>
> 请求url，/项目名/test/method.vea <br/>
> test：类名除Veasion之外的字符串，小写 <br/>
> method：将被调用的方法名 <br/>
 
> method定义：返回String，表示跳转的url或页面(默认转发，重定向请加redirect:). <br/>

> 该类中可以添加属性HttpServletRequest获取请求对象. <br/>
> 该类中可以添加属性HttpServletResponse获取响应对象. <br/>
> 该类中可以添加属性JSONObject获取分装的数据. <br/>
