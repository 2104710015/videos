Confrim={
  Show:function (message,callback) {
      Swal.fire({
          title: '您确定要操作?',
          text: message,
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '确定!',
          cancelButtonText: '取消'
      }).then((result) => {
          if (result.isConfirmed) {
                if(callback){
                    callback();
                }
            }
          })
  }
}