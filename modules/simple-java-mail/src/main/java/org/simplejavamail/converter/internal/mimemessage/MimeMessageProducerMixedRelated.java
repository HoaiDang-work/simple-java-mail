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
package org.simplejavamail.converter.internal.mimemessage;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import org.jetbrains.annotations.NotNull;
import org.simplejavamail.api.email.Email;

class MimeMessageProducerMixedRelated extends MimeMessageProducer {
	@Override
	boolean compatibleWithEmail(@NotNull Email email) {
		return emailContainsMixedContent(email) && emailContainsRelatedContent(email) && !emailContainsAlternativeContent(email);
	}
	
	@SuppressWarnings("Duplicates")
	@Override
	public void populateMimeMessageMultipartStructure(@NotNull MimeMessage message, @NotNull Email email)
			throws MessagingException, UnsupportedEncodingException {
		MultipartStructureWrapper multipartStructureWrapper = new MultipartStructureWrapper();
		
		MimeMessageHelper.setTexts(email, multipartStructureWrapper.multipartRelated, HEADERS_TO_POPULATE_TO_CHILDREN);
		MimeMessageHelper.configureForwarding(email, multipartStructureWrapper.multipartRootMixed);
		MimeMessageHelper.setEmbeddedImages(email, multipartStructureWrapper.multipartRelated);
		MimeMessageHelper.setAttachments(email, multipartStructureWrapper.multipartRootMixed);
		
		message.setContent(multipartStructureWrapper.multipartRootMixed);
	}
	
	/**
	 * refer to {@link MimeMessageProducerMixedRelatedAlternative}.
	 */
	private static class MultipartStructureWrapper {
		
		private final MimeMultipart multipartRootMixed;
		private final MimeMultipart multipartRelated;
		
		private MultipartStructureWrapper() {
			multipartRootMixed = new MimeMultipart("mixed");
			final MimeBodyPart contentRelated = new MimeBodyPart();
			multipartRelated = new MimeMultipart("related");
			try {
				// construct mail structure
				multipartRootMixed.addBodyPart(contentRelated);
				contentRelated.setContent(multipartRelated);
			} catch (final MessagingException e) {
				throw new MimeMessageProduceException(e.getMessage(), e);
			}
		}
	}
}