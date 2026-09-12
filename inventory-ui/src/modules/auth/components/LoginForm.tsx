import { ClipboardCheck, HelpCircle } from "lucide-react";
import { useState } from "react";
import { useTranslation } from "react-i18next";

import { useLogin } from "../hooks/useLogin";
import LanguageSwitcher from "./LanguageSwitcher";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [rememberMe, setRememberMe] = useState(false);

  const { handleLogin, loading, error } = useLogin();

  const { t } = useTranslation();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!username.trim() || !password.trim()) {
      return;
    }

    await handleLogin(username, password);
  };

  return (
    <div className="min-h-screen bg-neutral-100 flex flex-col font-sans text-neutral-800 overflow-x-hidden">
      {/* Header */}
      <header className="flex justify-between items-center px-4 sm:px-6 lg:px-8 py-4 sm:py-6">
        {/* Logo */}
        <div className="font-bold text-lg tracking-tight text-neutral-900">
          {t("app.name")}
        </div>

        {/* Header Actions */}
        <div className="flex items-center gap-3">
          <LanguageSwitcher />

          <button
            type="button"
            className="text-neutral-500 hover:text-neutral-700 transition-colors"
            aria-label={t("login.help")}
          >
            <HelpCircle className="w-5 h-5" />
          </button>
        </div>
      </header>

      {/* Main */}
      <main className="flex-1 flex items-center justify-center px-4 py-6">
        <div className="w-full max-w-md bg-white border border-neutral-200 shadow-sm p-5 sm:p-8">
          {/* Logo & Heading */}
          <div className="flex flex-col items-center mb-8">
            <div className="bg-black text-white p-3 mb-4">
              <ClipboardCheck className="w-6 h-6" />
            </div>

            <h1 className="text-lg sm:text-xl font-semibold text-neutral-900 text-center mb-2">
              {t("login.title")}
            </h1>

            <p className="text-xs text-neutral-500 text-center">
              {t("login.subtitle")}
            </p>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="space-y-4">
            {/* Username */}
            <div>
              <label
                htmlFor="username"
                className="block text-[10px] font-bold tracking-wider text-neutral-500 uppercase mb-1.5"
              >
                {t("login.username")}
              </label>

              <input
                id="username"
                type="text"
                placeholder={t("login.usernamePlaceholder")}
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="w-full px-3 py-2.5 text-sm border border-neutral-300 outline-none focus:border-black placeholder:text-neutral-400"
              />
            </div>

            {/* Password */}
            <div>
              <label
                htmlFor="password"
                className="block text-[10px] font-bold tracking-wider text-neutral-500 uppercase mb-1.5"
              >
                {t("login.password")}
              </label>

              <input
                id="password"
                type="password"
                placeholder={t("login.passwordPlaceholder")}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full px-3 py-2.5 text-sm border border-neutral-300 outline-none focus:border-black placeholder:text-neutral-400"
              />
            </div>

            {/* Remember Me */}
            <div className="flex items-center justify-between">
              <label className="flex items-center gap-2 text-xs text-neutral-600 cursor-pointer">
                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(e) => setRememberMe(e.target.checked)}
                />

                <span>{t("login.rememberMe")}</span>
              </label>
            </div>

            {/* Error */}
            {error && <p className="text-sm text-red-500">{error}</p>}

            {/* Login Button */}
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-black text-white text-xs font-semibold tracking-wider uppercase py-3 hover:bg-neutral-800 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? t("login.loggingIn") : t("login.login")}
            </button>
          </form>

          {/* Links */}
          <div className="mt-6 pt-6 border-t border-neutral-100 text-center space-y-2">
            <div>
              <button
                type="button"
                className="text-xs font-medium text-neutral-700 hover:underline"
              >
                {t("login.forgotPassword")}
              </button>
            </div>

            <div className="text-xs text-neutral-500">
              {t("login.noAccount")}{" "}
              <button
                type="button"
                className="font-medium text-neutral-900 hover:underline"
              >
                {t("login.requestAccess")}
              </button>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="border-t border-neutral-200 px-4 sm:px-6 lg:px-8 py-6">
        <div className="flex flex-col lg:flex-row items-center justify-between gap-4 text-[11px] text-neutral-500">
          <div className="font-semibold tracking-tight text-neutral-800">
            INVENTORYFLOW
          </div>

          <div className="flex flex-wrap justify-center gap-4 tracking-wide">
            <a href="#privacy" className="hover:text-neutral-800">
              {t("footer.privacy")}
            </a>

            <a href="#terms" className="hover:text-neutral-800">
              {t("footer.terms")}
            </a>

            <a href="#help" className="hover:text-neutral-800">
              {t("footer.helpCenter")}
            </a>
          </div>

          <div className="text-center">{t("footer.copyright")}</div>
        </div>
      </footer>
    </div>
  );
};

export default LoginForm;
