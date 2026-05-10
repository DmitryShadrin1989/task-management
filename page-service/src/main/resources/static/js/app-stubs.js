(function () {
    var BASE = 'This feature is not implemented yet.';

    function ensureRoot() {
        var el = document.getElementById('appToastRoot');
        if (!el) {
            el = document.createElement('div');
            el.id = 'appToastRoot';
            el.className = 'app-toast-root';
            el.setAttribute('aria-live', 'polite');
            document.body.appendChild(el);
        }
        return el;
    }

    window.showFeatureNotImplemented = function (detail) {
        var root = ensureRoot();
        var toast = document.createElement('div');
        toast.className = 'app-toast';
        toast.setAttribute('role', 'status');

        var main = document.createElement('p');
        main.className = 'app-toast-line app-toast-line--main';
        main.textContent = BASE;
        toast.appendChild(main);

        if (detail) {
            var sub = document.createElement('p');
            sub.className = 'app-toast-line app-toast-line--detail';
            sub.textContent = detail;
            toast.appendChild(sub);
        }

        root.appendChild(toast);

        requestAnimationFrame(function () {
            toast.classList.add('app-toast--visible');
        });

        var hideTimer;
        function removeToast() {
            clearTimeout(hideTimer);
            toast.removeEventListener('click', removeToast);
            toast.classList.remove('app-toast--visible');
            setTimeout(function () {
                if (toast.parentNode) {
                    toast.parentNode.removeChild(toast);
                }
            }, 280);
        }

        hideTimer = setTimeout(removeToast, 4500);
        toast.addEventListener('click', removeToast);
    };
})();
