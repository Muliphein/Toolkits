# Toolkits

用于访问文件夹下的图片

将相册存放在`Android/app/io.github.muliphein.toolkits`下，为文件夹格式，然后该app会把每个文件夹下的所有图片文件当作一个相册进行浏览（可以嵌套）。

没什么用，做出来看图用的。比如说下载了漫画之类的，只需要按照文件夹分开，然后就可以用这个app看了。

有一些好处，这个位置不会被系统相册扫描，不用担心放在这里会污染相册。其次就是为了流畅度还做了一些异步的读取图片，应该会因为这个有bug，但是也基本够用了。

#### 相册一览
![Gallery](https://github.com/Muliphein/Toolkits/blob/main/assets/Gallery.jpg)


#### 浏览内容
![Browsing](https://github.com/Muliphein/Toolkits/blob/main/assets/Browsing.jpg)