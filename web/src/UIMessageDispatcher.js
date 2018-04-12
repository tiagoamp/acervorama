import PNotify from 'pnotify/dist/es';

export default class UIMessageDispatcher {

    static showSuccessMessage(msg) {
        return PNotify.success({
            title: 'Success!',
            text: msg,
            delay:3000,
            shadow:true,
            hide:true,
            nonblock:false,
            desktop:false
        });
    }

    static showErrorMessage(msg) {
        return PNotify.error({
            title: 'Error!',
            text: msg,
            delay:3000,
            shadow:true,
            hide:true,
            nonblock:false,
            desktop:false
        });
    }

    static showInfoMessage(msg) {
        return PNotify.info({
            title: 'Info!',
            text: msg,
            delay:3000,
            shadow:true,
            hide:true,
            nonblock:false,
            desktop:false
        });
    }
    
}
