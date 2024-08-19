import sys
print(sys.path)
import json
#coding:utf-8
import urllib
from contextlib import closing



def fetch_posts():
    print("1111");
    url = 'http://jsonplaceholder.typicode.com/posts'
    try:

        with closing(urllib.urlopen(url)) as response:
            if response.getcode() == 200:
                source = response.read()
                posts = json.loads(source)
                print(posts)
                return posts
            else:
                print("Failed to fetch posts due to HTTP error.")
    except Exception as e:
        print("error....");
        print(e);
        return None


fetch_posts();