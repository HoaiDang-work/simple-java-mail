/*
 * Copyright © 2009 Benny Bottema (benny@bennybottema.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.simplejavamail.mailer.internal;

import com.sanctionco.jmail.EmailValidator;
import org.jetbrains.annotations.Nullable;
import org.simplejavamail.api.mailer.config.EmailGovernance;
import org.simplejavamail.api.mailer.config.Pkcs12Config;

/**
 * @see EmailGovernance
 */
class EmailGovernanceImpl implements EmailGovernance {
	@Nullable private final EmailValidator emailValidator;
	@Nullable private final Pkcs12Config pkcs12ConfigForSmimeSigning;

	EmailGovernanceImpl(@Nullable final EmailValidator emailValidator, @Nullable final Pkcs12Config pkcs12ConfigForSmimeSigning) {
		this.emailValidator = emailValidator;
		this.pkcs12ConfigForSmimeSigning = pkcs12ConfigForSmimeSigning;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EmailGovernanceImpl{");
		sb.append("emailValidator=").append(emailValidator);
		sb.append(", pkcs12ConfigForSmimeSigning=").append(pkcs12ConfigForSmimeSigning);
		sb.append('}');
		return sb.toString();
	}

	/**
	 * @see EmailGovernance#getEmailValidator()
	 */
	@Override
	public @Nullable EmailValidator getEmailValidator() {
		return emailValidator;
	}

	/**
	 * @see EmailGovernance#getPkcs12ConfigForSmimeSigning()
	 */
	@Override
	public @Nullable Pkcs12Config getPkcs12ConfigForSmimeSigning() {
		return pkcs12ConfigForSmimeSigning;
	}
}