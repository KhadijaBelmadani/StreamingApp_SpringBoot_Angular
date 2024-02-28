import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-ydkbsju468c577qs.us.auth0.com',
            redirectUrl: window.location.origin,
            clientId: '4ef7a53nwPWzFdwAxJcPWMj4h7njp5mF',
            scope: 'openid profile offline_access email',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            secureRoutes: ['http://localhost:8080/'],
            customParamsAuthRequest: {
            audience: 'http://localhost:8080/'
          }
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
