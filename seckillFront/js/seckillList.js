$(document).ready(function(){
   $.ajax({
       type:"GET",
       url:"http://localhost:8080/seckill/list/",
       dataType:'json',
       global:"false",
       success:function(data) {
           var seckillList = data;

           for(var i=0; i<seckillList.length; i++) {
               var seckillId = seckillList[i].seckillId;
               var name = seckillList[i].name;
               var number = seckillList[i].number;
               var start_time = seckillList[i].startTime;
               var start_date = new Date(start_time);
               var end_time = seckillList[i].endTime;
               var end_date= new Date(end_time);
               var create_time = seckillList[i].createTime;
               var create_date = new Date(create_time);
               var Url = "'../html/detail.html?seckil=" + seckillId+"'";
               var div = $("<tr class='seckillList'><td>"+
                            name+"</td><td>"+number+"</td><td>"+
                            start_date.toLocaleString()+"</td><td>"+
                            end_date.toLocaleString()+"</td><td>"+
                            create_date.toLocaleString()+
                            "</td><td><a class='btn btn-info' href="+Url+">Link</a></td></tr>");
               $('tbody').append(div);
             ;
           }
       }
   });
})