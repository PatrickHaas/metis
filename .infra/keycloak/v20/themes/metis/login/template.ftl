<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true displayRequiredFields=false displayWide=false showAnotherWayIfPresent=true>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml" class="${properties.kcHtmlClass!}">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="robots" content="noindex, nofollow">

        <#if properties.meta?has_content>
            <#list properties.meta?split(' ') as meta>
                <meta name="${meta?split('==')[0]}" content="${meta?split('==')[1]}"/>
            </#list>
        </#if>
        <title>${msg("loginTitle",(realm.displayName!''))}</title>
        <link rel="icon" href="${url.resourcesPath}/img/favicon.ico"/>
        <#if properties.styles?has_content>
            <#list properties.styles?split(' ') as style>
                <link href="${url.resourcesPath}/${style}" rel="stylesheet"/>
            </#list>
        </#if>
        <#if properties.scripts?has_content>
            <#list properties.scripts?split(' ') as script>
            <#--
            <script src="${url.resourcesPath}/${script}" type="text/javascript"></script>
            <script src="${url.resourcesPath}/${script}" type="module"></script>
            -->
                <script src="${url.resourcesPath}/${script}" type="text/javascript"></script>
            </#list>
        </#if>
        <#if scripts??>
            <#list scripts as script>
                <script src="${script}" type="text/javascript"></script>
            </#list>
        </#if>
    </head>

    <body class="container pt-3 bg-light">
    <div class="">
        <div id="kc-header" class="${properties.kcHeaderClass!}">
            <#-- <div id="kc-header-wrapper" class="${properties.kcHeaderWrapperClass!}">${kcSanitize(msg("loginTitleHtml",(realm.displayNameHtml!'')))?no_esc}</div> -->
        </div>
        <div class="${properties.kcFormCardClass!} <#if displayWide>${properties.kcFormCardAccountClass!}</#if>">
            <header class="${properties.kcFormHeaderClass!}">
                <#if !(auth?has_content && auth.showUsername() && !auth.showResetCredentials())>
                <#else>
                    <#if displayRequiredFields>
                        <div class="${properties.kcContentWrapperClass!}">
                            <div class="${properties.kcLabelWrapperClass!} subtitle">
                                <span class="subtitle"><span class="required">*</span> ${msg("requiredFields")}</span>
                            </div>
                            <div class="col-md-10">
                                <#nested "show-username">
                                <div class="${properties.kcFormGroupClass!}">
                                    <div id="kc-username">
                                        <label id="kc-attempted-username">${auth.attemptedUsername}</label>
                                        <a id="reset-login" href="${url.loginRestartFlowUrl}">
                                            <div class="kc-login-tooltip">
                                                <i class="${properties.kcResetFlowIcon!}"></i>
                                                <span class="kc-tooltip-text">${msg("restartLoginTooltip")}</span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <#else>
                        <#nested "show-username">
                        <p class="text-center mt-5 mb-4">
                            <img src="${url.resourcesPath}/img/shield-color.svg" height="96" width="96"
                                 class="rounded-3"/>
                        </p>
                        <div class="row">
                            <div class="col-12 col-md-6 col-lg-4 offset-md-3 offset-lg-4 mb-4">
                                <a class="btn btn-secondary mt-3 w-100" role="button"
                                   href="${url.loginRestartFlowUrl}">${msg("restartLoginTooltip")}</a>
                            </div>
                        </div>


                    </#if>
                </#if>
            </header>
            <div class="row">
                <div class="col-12 col-md-6 col-lg-4 offset-md-3 offset-lg-4">
                    <div id="kc-content">
                        <div id="kc-content-wrapper">

                            <#-- App-initiated actions should not see warning messages about the need to complete the action -->
                            <#-- during login.                                                                               -->
                            <#if displayMessage && message?has_content && (message.type != 'warning' || !isAppInitiatedAction??)>
                                <#if message.type = 'success'>
                                    <div class="alert alert-success mb-4">
                                        ${kcSanitize(message.summary)?no_esc}
                                    </div>
                                </#if>
                                <#if message.type = 'warning'>
                                    <div class="alert alert-warning mb-4">
                                        ${kcSanitize(message.summary)?no_esc}
                                    </div>
                                </#if>
                                <#if message.type = 'error'>
                                    <div class="alert alert-danger mb-4">
                                        ${kcSanitize(message.summary)?no_esc}
                                    </div>
                                </#if>
                                <#if message.type = 'info'>
                                    <div class="alert alert-primary mb-4">
                                        ${kcSanitize(message.summary)?no_esc}
                                    </div>
                                </#if>
                            </#if>

                            <#nested "form">

                            <#if displayInfo>
                                <div id="kc-info" class="${properties.kcSignUpClass!}">
                                    <div id="kc-info-wrapper" class="${properties.kcInfoAreaWrapperClass!}">
                                        <#nested "info">
                                    </div>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    </body>
    </html>
</#macro>