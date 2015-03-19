# -*- coding:utf-8 -*-
import urllib
import urllib2
import re
import cookielib
import json
from BeautifulSoup import BeautifulSoup  
from threading import Timer
import time

cj = cookielib.LWPCookieJar()
cookie_support = urllib2.HTTPCookieProcessor(cj)
opener = urllib2.build_opener(cookie_support, urllib2.HTTPHandler)  
urllib2.install_opener(opener)


url="http://jiyou.11185.cn"
posturl = "http://jiyou.11185.cn/login.html"
indexurl = "http://jiyou.11185.cn/index.html"
header ={   
                'Proxy-Connection' : 'keep-alive',
                'User-Agent' : 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:14.0) Gecko/20100101 Firefox/14.0.1'
                }
values = {'action':'login',
		  'username' : 'xjf928',
          'password' :'xjf928'}

data = urllib.urlencode(values)
h = urllib2.urlopen(url)
request = urllib2.Request(posturl,data,header)
response = urllib2.urlopen(request)
html = response.read().decode("utf-8").encode("utf-8")
index = urllib2.urlopen(indexurl)
indexhtml = index.read().decode("utf-8").encode("utf-8")
soup = BeautifulSoup(indexhtml)

initContent = [[] for i in range(7)]
queryContent = [[] for i in range(7)]
initHref = [[] for i in range(7)]
queryHref = [[] for i in range(7)]

def initList(k,div):
	p = div.findAll('p',attrs={"class" : "bt1"})
	for i in p:
		content = i.contents[0].string
		href = i.a["href"]
		initContent[k].append(content)
		initHref[k].append(href)
		

def queryList(k,div):
	p = div.findAll('p',attrs={"class" : "bt1"})
	for i in p:
		content = i.contents[0].string
		href = i.a["href"]
		queryContent[k].append(content)
		queryHref[k].append(href)
		


div1 = soup.find('div',attrs={"class" : "floor1"})
div2 = soup.find('div',attrs={"class" : "floor2"})
div3 = soup.find('div',attrs={"class" : "floor3"})
div4 = soup.find('div',attrs={"class" : "floor4"})
div6 = soup.find('div',attrs={"class" : "floor6"})
initList(1,div1)
initList(2,div2)
initList(3,div3)
initList(4,div4)
initList(6,div6)



# for k in initContent:
# 	for i in k:
# 		print i

# for k in initHref:
# 	for i in k:
# 		print i

addUrl = "http://jiyou.11185.cn/widget?type=shop_cart&action=add&ajax=yes"

postValue = {
	'num':'1',
	'productid':'701111',
	'itemtype':'4',
	'type_id':'49',
	'depot_id':'1',
	'action':'add'
}
def Add(postValue1):
    postData = urllib.urlencode(postValue1)
    AddRequest = urllib2.Request(addUrl,postData,header)
    Addresponse = urllib2.urlopen(AddRequest)
    text = Addresponse.read().decode("utf-8").encode("utf-8")
    jsontext = json.loads(text)
    # print text
    if jsontext["result"]==0:
    	print 'fail'
    	return 0
    else:
    	print 'success'
    	return 1


def run(postValue2):
	state = 1
	while True:
		if state==0:
			print 'buy success'
			break	
		time.sleep(1)
		if Add(postValue2)==1:
			state=0

uinput = raw_input("input id")
postValue['productid']=uinput


run(postValue)
# if Add(postValue)==1:
    		# 	state = 02
# 注释
