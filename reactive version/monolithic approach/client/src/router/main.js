export default [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/main/Home.vue'),
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/main/About.vue')
    },
    {
        path: '/settings',
        name: 'Settings',
        component: () => import('../views/main/Settings.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/settings/edit',
        name: 'Edit',
        component: () => import('../views/main/Edit.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/settings/account-remove',
        name: 'DeleteAccount',
        component: () => import('../views/main/DeleteAccount.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/settings/logout',
        name: 'Logout',
        component: () => import('../views/main/Logout.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/main/CreateAccount.vue'),
        meta: {requiresGuest: true}
    },
    {
        path: '/nutri-info',
        name: 'NutriInfo',
        component: () => import('../views/main/NutriInfo.vue')
    },
    {
        path: '/sign-login',
        name: 'SignUpOrLogin',
        component: () => import('../views/main/SignUpOrLogin.vue'),
        meta: {requiresGuest: true}
    },
];