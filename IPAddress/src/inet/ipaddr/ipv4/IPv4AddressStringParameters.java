package inet.ipaddr.ipv4;

import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IPAddressStringParameters.IPVersionAddressStringParameters;
import inet.ipaddr.IPAddressStringParameters.RangeParameters;

/**
 * The IPv4-specific parameters within a {@link IPAddressStringParameters} instance.
 * 
 * @author sfoley
 *
 */
public class IPv4AddressStringParameters extends IPVersionAddressStringParameters implements Comparable<IPv4AddressStringParameters> {
	
	private static final long serialVersionUID = 1L;

	public static final boolean DEFAULT_ALLOW_IPV4_INET_ATON = true;
	public static final boolean DEFAULT_ALLOW_IPV4_INET_ATON_SINGLE_SEGMENT_MASK = false; //When not allowing prefixes beyond address size, whether 1.2.3.4/33 has a mask of ipv4 address 33 rather than treating it like a prefix
	
	/**
	 * Allows ipv4 inet_aton hexadecimal format 0xa.0xb.0xc.0cd
	 */
	public final boolean inet_aton_hex;
	
	/**
	 * Allows ipv4 inet_aton octal format, 04.05.06.07 being an example.
	 * Can be overridden by {@link IPVersionAddressStringParameters#allowLeadingZeros}
	 */
	public final boolean inet_aton_octal;
	
	/**
	 * Allows ipv4 joined segments like 1.2.3, 1.2, or just 1
	 */
	public final boolean inet_aton_joinedSegments;
	
	/**
	 * If you allow ipv4 joined segments, whether you allow a mask that looks like a prefix length: 1.2.3.5/255
	 */
	public final boolean inet_aton_single_segment_mask;
	
	public IPv4AddressStringParameters(
			boolean allowLeadingZeros,
			boolean allowCIDRPrefixLeadingZeros,
			boolean allowUnlimitedLeadingZeros,
			RangeParameters rangeOptions,
			boolean allowWildcardedSeparator,
			boolean allowPrefixesBeyondAddressSize,
			boolean inet_aton_hex,
			boolean inet_aton_octal,
			boolean inet_aton_joinedSegments,
			boolean inet_aton_single_segment_mask) {
		super(allowLeadingZeros, allowCIDRPrefixLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, rangeOptions.allowsWildcard() && allowWildcardedSeparator, allowPrefixesBeyondAddressSize);
		this.inet_aton_hex = inet_aton_hex;
		this.inet_aton_octal = inet_aton_octal;
		this.inet_aton_joinedSegments = inet_aton_joinedSegments;
		this.inet_aton_single_segment_mask = inet_aton_single_segment_mask;
	}
	
	public Builder toBuilder() {
		Builder builder = new Builder();
		builder.inet_aton_hex = inet_aton_hex;
		builder.inet_aton_octal = inet_aton_octal;
		builder.inet_aton_joinedSegments = inet_aton_joinedSegments;
		return (Builder) toBuilder(builder);
	}
	
	public static class Builder extends IPVersionAddressStringParameters.BuilderBase {
		private boolean inet_aton_hex = DEFAULT_ALLOW_IPV4_INET_ATON;
		private boolean inet_aton_octal = DEFAULT_ALLOW_IPV4_INET_ATON;
		private boolean inet_aton_joinedSegments = DEFAULT_ALLOW_IPV4_INET_ATON;
		private boolean inet_aton_single_segment_mask = DEFAULT_ALLOW_IPV4_INET_ATON_SINGLE_SEGMENT_MASK;
		
		public Builder allow_inet_aton(boolean allow) {
			inet_aton_joinedSegments = inet_aton_octal = inet_aton_hex = allow;
			super.allowUnlimitedLeadingZeros(allow);
			return this;
		}
		
		/**
		 * @see IPv4AddressStringParameters#inet_aton_hex
		 * @param allow
		 * @return the builder
		 */
		public Builder allow_inet_aton_hex(boolean allow) {
			inet_aton_hex = allow;
			return this;
		}
		
		/**
		 * @see IPv4AddressStringParameters#inet_aton_octal
		 * @param allow
		 * @return the builder
		 */
		public Builder allow_inet_aton_octal(boolean allow) {
			inet_aton_octal = allow;
			return this;
		}
		
		/**
		 * @see IPv4AddressStringParameters#inet_aton_joinedSegments
		 * @param allow
		 * @return the builder
		 */
		public Builder allow_inet_aton_joined_segments(boolean allow) {
			inet_aton_joinedSegments = allow;
			return this;
		}
		
		/**
		 * @see IPv4AddressStringParameters#inet_aton_single_segment_mask
		 * @param allow
		 * @return the builder
		 */
		public Builder allow_inet_aton_single_segment_mask(boolean allow) {
			inet_aton_single_segment_mask = allow;
			return this;
		}
		
		@Override
		public Builder setRangeOptions(RangeParameters rangeOptions) {
			super.setRangeOptions(rangeOptions);
			return this;
		}
		
		@Override
		public Builder allowPrefixesBeyondAddressSize(boolean allow) {
			super.allowPrefixesBeyondAddressSize(allow);
			return this;
		}
		
		@Override
		public Builder allowWildcardedSeparator(boolean allow) {
			super.allowWildcardedSeparator(allow);
			return this;
		}
		
		@Override
		public Builder allowLeadingZeros(boolean allow) {
			super.allowLeadingZeros(allow);
			return this;
		}
		
		@Override
		public Builder allowPrefixLengthLeadingZeros(boolean allow) {
			super.allowPrefixLengthLeadingZeros(allow);
			return this;
		}
		
		@Override
		public Builder allowUnlimitedLeadingZeros(boolean allow) {
			super.allowUnlimitedLeadingZeros(allow);
			return this;
		}
		
		public IPv4AddressStringParameters toParams() {
			return new IPv4AddressStringParameters(allowLeadingZeros, allowPrefixLengthLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, 
					allowWildcardedSeparator, allowPrefixesBeyondAddressSize, inet_aton_hex, inet_aton_octal, inet_aton_joinedSegments, inet_aton_single_segment_mask);
		}
	}
	
	@Override
	public IPv4AddressStringParameters clone() {
		try {
			return (IPv4AddressStringParameters) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	public int compareTo(IPv4AddressStringParameters o) {
		int result = super.compareTo(o);
		if(result == 0) {
			result = Boolean.compare(inet_aton_hex, o.inet_aton_hex);
			if(result == 0) {
				result = Boolean.compare(inet_aton_octal, o.inet_aton_octal);
				if(result == 0) {
					result = Boolean.compare(inet_aton_joinedSegments, o.inet_aton_joinedSegments);
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof IPv4AddressStringParameters) {
			if(super.equals(o)) {
				IPv4AddressStringParameters other = (IPv4AddressStringParameters) o;
				return inet_aton_hex == other.inet_aton_hex
						&& inet_aton_octal == other.inet_aton_octal
						&& inet_aton_joinedSegments == other.inet_aton_joinedSegments;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = super.hashCode();//super hash code uses only first 6 bits
		if(inet_aton_hex) {
			hash |= 0x40;//7th bit
		}
		if(inet_aton_octal) {
			hash |= 0x80;//8th bit
		}
		if(inet_aton_joinedSegments) {
			hash |= 0x100;//9th bit
		}
		return hash;
	}
}
