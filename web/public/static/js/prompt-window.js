
const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 1000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})


prompt = {
    success: function (message) {
        Toast.fire({
            icon: 'success',
            title: message
        });

    },

    error: function (message) {
        Toast.fire({
            icon: 'error',
            title: message
        });
    },

    warning: function (message) {
        Toast.fire({
            icon: 'warning',
            title: message
        });
    }
};