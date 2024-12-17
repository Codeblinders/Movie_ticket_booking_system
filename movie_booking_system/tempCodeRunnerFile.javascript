function User(username,email,password){
    this.username=username
    this.email=email
    this.password=password
}



User.prototype.changeusername=function(){
    return `${this.username.toUpperCase()}`
}

const chai=new User("chai",'chai@gmail.com','2323')

console.log(chai.changeusername())