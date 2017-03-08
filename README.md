# Workdone [![Build Status](https://travis-ci.org/PoprostuRonin/workdone.svg?branch=master)](https://travis-ci.org/PoprostuRonin/workdone)

**WORK IN PROGRESS**

Workdone is a to-do-list, habit tracker game that helps getting work done. You can use Workdone by your favourite
browser, Chrome app or Android app.

## Planned features
* To-do list with categories, time to complete
* Habits tracking
* Good/bad behaviours rewarding
* Groups

## Technologies
*Spring* as a backend, core of the application. *Vue.js* and *MDL* as a frontend kings.
Android app built using SDK or *React Native*.

## About
This is a contest project for [Get noticed!](http://dajsiepoznac.pl) where I blog (on [pr0gramista.pl](https://pr0gramista.pl), in Polish) 
about IT and this specific project.

#### Configuration:
In application.yml:
```
security:
  oauth2:
    client:
      clientId: <put your id>
      clientSecret: <put your secret>
      accessTokenUri: https://www.googleapis.com/oauth2/v4/token
      userAuthorizationUri: https://accounts.google.com/o/oauth2/v2/auth
      clientAuthenticationScheme: form
      scope:
        - openid
        - email
        - profile
    resource:
      userInfoUri: https://www.googleapis.com/oauth2/v3/userinfo
      preferTokenInfo: true
```